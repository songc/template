package com.dt.spark.template;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * Created by songc on 2016/9/11 0011.
 */
public class CompareWithTemplate {
    public static double corrValue(double[] data, double[] template,int rate) {
        //声明一个double值用于保存最后结果并返回。
        double result=0;
        //声明int类型保存波峰的位置
        int peakInData,peakInTemplate;
        peakInData = findPeaks(data,rate);
        peakInTemplate = findPeaks(template,rate);
        if (peakInData == -1) {
            result=0;
        }
//       data = MathMethods.arraysMinus(data, MathMethods.minInArrays(data));
        if (peakInData < peakInTemplate) {
//            Double[] addArrays = new Double[peakInTemplate-peakInData];
//            Double arraysValue = new Double(data[0]);
//            addArrays=(Double[])MathMethods.creatArrays(arraysValue, peakInTemplate - peakInData);
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[0], peakInTemplate - peakInData);
            data = MathMethods.arraysMerge(addArrays, data);
        } else if (peakInData > peakInTemplate) {
            double[] addArrays = MathMethods.creatArrays(template[0], peakInData - peakInTemplate);
            template = MathMethods.arraysMerge(addArrays, template);
        }
        if (data.length < template.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[data.length - 1], template.length - data.length);
            data = MathMethods.arraysMerge(data, addArrays);
        } else if (data.length > template.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(template[template.length - 1], data.length - template.length);
            template = MathMethods.arraysMerge(template, addArrays);
        }
        if (data.length != template.length) {
            return result=0;
        }
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        result = pearsonsCorrelation.correlation(data, template);
        return result;
    }

    public static int findPeaks(double[] data,int rate) {
        //声明int类型保存波峰的位置
        int peakInData=-1;
        //求出data中的最小值
        double minData = MathMethods.minInArrays(data);
        for (int i=1;i<data.length-1;i++) {
            if (data[i - 1] < data[i] && data[i] >= data[i + 1] && i >= rate && Math.abs(data[i] - data[0]) >= 6
                    && (data[i] == minData)) {
                peakInData=i;
                break;
            } else if (data[i - 1] > data[i] && data[i] <= data[i + 1] && i >= rate && Math.abs(data[i] - data[0]) >= 6 &&
                    (data[i] == minData)) {
                peakInData = i;
                break;
            } else {
                peakInData=-1;
            }
        }
        return peakInData;
    }
    //用于对齐波峰并使数组长度一致。
    public static double[] mergeArrays(double[] data,double[] template,int rate) {
        double[] result;
        int peakInData,peakInTemplate;
        peakInData = findPeaks(data,rate);
        peakInTemplate = findPeaks(template,rate);
        data = MathMethods.arraysMinus(data, MathMethods.minInArrays(data));
        if (peakInData < peakInTemplate) {
//            Double[] addArrays = new Double[peakInTemplate-peakInData];
//            Double arraysValue = new Double(data[0]);
//            addArrays=(Double[])MathMethods.creatArrays(arraysValue, peakInTemplate - peakInData);
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[0], peakInTemplate - peakInData);
            data = MathMethods.arraysMerge(addArrays, data);
        } else if (peakInData > peakInTemplate) {
            double[] addArrays = MathMethods.creatArrays(template[0], peakInData - peakInTemplate);
            template = MathMethods.arraysMerge(addArrays, template);
        }
        if (data.length < template.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[data.length - 1], template.length - data.length);
            data = MathMethods.arraysMerge(data, addArrays);
        } else if (data.length > template.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(template[template.length - 1], data.length - template.length);
            template = MathMethods.arraysMerge(template, addArrays);
        }
        if (data.length == template.length) {
            result = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                result[i] = (data[i] + template[i]) / 2;
            }
            return result;
        } else {
            result= new double[0];
            return result;
        }
    }
}
