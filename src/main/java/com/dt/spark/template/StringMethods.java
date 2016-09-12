package com.dt.spark.template;

import scala.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songc on 2016/9/11 0011.
 */
public class StringMethods {
    public static String[][] getTwoDimeString(String[] readString) {
        String[][] str2 = new String[readString.length][];
        int i = 0;
        for (String tmpString :
                readString) {
            str2[i] = tmpString.split(",");
//			for (String tmp :
//					str2[i]){
//				System.out.println(tmp);
//			}
            i++;
        }
        return str2;
    }

    public static double[][] getDouble(String[][] arrays) {
        double[][] result = new double[arrays.length][arrays[0].length];
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                result[i][j]=Double.parseDouble(arrays[i][j]);
//                System.out.println(result[i][j]);
            }
        }
        return result;
    }

    public static double[] getDouble(String[] arrays) {
        ArrayList<Double> result = new ArrayList<Double>();
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i]!= "") {
                result.add(Double.parseDouble(arrays[i]));
            }
        }
        double[] finalResult=new double[result.size()];
        int i=0;
        for (double tmp :
                result) {
            finalResult[i]=result.get(i);
            i++;
        }
        return finalResult;
    }
    //截取指定部分的ArrayList。
    public static List getSubList(List arrayList, int start, int ends) {
        List result = new ArrayList();
        for (int i = start;i<ends;i++) {
            result.add(arrayList.get(i));
        }
        return  result;
    }

    public static ArrayList getListFromArray(double[] arrays) {
        ArrayList result = new ArrayList();
        for (double tmp :
                arrays) {
            result.add(tmp + "");
        }
        return result;
    }

    public static List<Double> getDoubleFromList(List<String> list) {
        List<Double> result = new ArrayList<Double>();
        for (String tmp :
                list) {
            if (tmp.trim().length() != 0) {
                result.add(Double.parseDouble(tmp));
            }
        }
        return result;
    }
 private static final int LABEL_POSITION=0;
    public static Tuple2<List<Double>, String> getTuple2FromList(List<String> list) {
//        Map<List<Double>, String> result = new HashMap<>();
//        for (int i = 0; i < list.size(); i++) {
           Tuple2 result = new Tuple2(StringMethods.getDoubleFromList(StringMethods.getSubList(list, 1, list.size())), list.get(LABEL_POSITION));
//        }
        return result;
    }
}
