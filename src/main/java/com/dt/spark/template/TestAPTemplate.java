package com.dt.spark.template;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.json4s.DefaultWriters;
import scala.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songc on 2016/9/10 0010.
 */
public class TestAPTemplate {
    //设置全局静态变量分别是模板的文件位置，待测数据的文件位置，和取样的频率
    public static final String TEMPLATE_FILE_PATH="E:\\文件\\AP\\AP_template_2016_5_24_delete.xlsx";
    public static final String DATA_FILE_PATH="E:\\文件\\AP\\AP-all-2016-6-23-data-329.xlsx";
    public static final int RATE=10;

    public static void main(String[] args) throws IOException {

        //首先创建了一个SparkContext
        SparkConf sparkConf = new SparkConf().setAppName("TesAPTemplate").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        //先读取计算所需要的数据到内存中，数据在excel文件中，要采用excel API读取
        ExcelReader excelReader= new ExcelReader();
        List<List<String>> template = excelReader.readExcelByColumns(TEMPLATE_FILE_PATH, 0);
        List<List<String>> testData = excelReader.readExcel(DATA_FILE_PATH,1);
        List<List<Double>> templateDouble = new ArrayList<List<Double>>();
        for (int i = 0; i < template.size(); i++) {
            templateDouble.add(StringMethods.getDoubleFromList(template.get(i)));
        }
//        JavaRDD<List<String>> templateRDD = sc.parallelize(template);
        JavaRDD<List<String>> testDataRDD = sc.parallelize(testData);
//        JavaRDD<List<Double>> templateDouble = templateRDD.map(s->StringMethods.getDoubleFromList(s));
        JavaPairRDD<List<Double>, String> testDaraAndLabel = testDataRDD.mapToPair(s -> StringMethods.getTuple2FromList(s));
        JavaPairRDD<List<Double>, List<Double>> testDataAndDegree = testDaraAndLabel.mapToPair(s -> degree(s._1, templateDouble));
        JavaPairRDD<List<Double>, Double> testDaraAndMaxDegree = testDataAndDegree.mapToPair(s -> maxDegree(s._1, s._2));
        JavaPairRDD result = testDaraAndMaxDegree.cogroup(testDaraAndLabel).filter(s -> isCorrect(s._2._1, s._2._2));
        long correctNum= result.count();
        long sum= testDaraAndMaxDegree.count();
        System.out.println("正确个数：" + correctNum);
        System.out.println("测试总数是："+ sum);
        System.out.println("正确率是："+(double)correctNum/sum);


    }

    public static void println(Tuple2<List<Double>,Tuple2> data) {
        System.out.println(data._2);
    }

    public static boolean isCorrect(Iterable<Double> degree, Iterable<String> label) {
        if ((degree.iterator().next()>= 0.9 && label.iterator().next().endsWith("1")) || (degree.iterator().next() < 0.9 && label.iterator().next().endsWith("0"))) {
            return true;
        } else {
            return false;
        }
    }

    public static Tuple2<List<Double>, Double> maxDegree(List<Double> test, List<Double> degree) {
        Double maxDegree = degree.get(0);
        for (double tmp :
                degree) {
            if (maxDegree < tmp) {
                maxDegree=tmp;
            }
        }
        Tuple2<List<Double>, Double> result = new Tuple2<>(test, maxDegree);
        return result;

    }

    public static Tuple2<List<Double>, List<Double>> degree(List<Double> test, List<List<Double>> template) {

        List<Double> degree = new ArrayList<>();
        Double[] testArrays = test.toArray(new Double[0]);
        for (List<Double> tmp:
                template){
            Double[] templateArrays=tmp.toArray(new Double[0]);
            degree.add(CompareWithTemplate.corrValue(MathMethods.doubleArraysFromDouble(testArrays), MathMethods.doubleArraysFromDouble(templateArrays), RATE));
        }
        Tuple2<List<Double>, List<Double>> result = new Tuple2<>(test, degree);
        return result;
    }
}
