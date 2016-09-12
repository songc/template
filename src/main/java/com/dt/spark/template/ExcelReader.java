package com.dt.spark.template;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作Excel表的功能类
 * Created by songc on 2016/8/25.
 */
public class ExcelReader {
//    public static final String filePath="F:\\AP\\AP_template_2016_5_24_delete.xlsx";

    public double[][] readExcel(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = null;
        //这边还有点问题要改下
        double[][] result=new double[sheet.getLastRowNum()+1][8];
        for (int i = 0; sheet.getRow(i) !=null ; i++) {
            row = sheet.getRow(i);
            for (int j = 0; row.getCell(j) !=null ; j++) {
                result[i][j]=Double.parseDouble(row.getCell(j).toString());
            }
        }
        return result;
    }
    //读取Excel文件返回字符串数组list.
    List<List<String>> readExcel(String filePath, int sheetNum) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook= new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(sheetNum);
        XSSFRow row=null;
        List<List<String>> result = new ArrayList<List<String>>();
        for (int i = 0; i<sheet.getLastRowNum()+1; i++) {
            List<String> rowList = new ArrayList<String>();
            row=sheet.getRow(i);
            for (int j = 0; j<row.getLastCellNum(); j++) {
                rowList.add(row.getCell(j).toString());
            }
            result.add(rowList);
        }
        return result;
    }

    //按列读取Excel数据
    List<List<String>> readExcelByColumns(String filePath, int sheetNum) throws IOException {
        //创建文件流对象
        FileInputStream fileInputStream = new FileInputStream(filePath);
        //通过文件流对象创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        //通过工作簿创建一个电子表对象
        XSSFSheet sheet = workbook.getSheetAt(sheetNum);
//        //创建一个空的列对象
//        XSSFRow row = null;
        List<List<String>> result = new ArrayList<List<String>>();
        //迭代循环列
        for (int i = 0; sheet.getRow(0).getCell(i)!=null; i++) {
            //创建一个ArrayList对象存放每一列的数据
            List<String> column = new ArrayList<String>();
            //迭代循环每一行
            for (int j = 0;j<sheet.getLastRowNum()+1; j++) {
                //如果某一行的该列元素为空，说明该行以后的所有行都为空。终止循环
                if (sheet.getRow(j).getCell(i) == null) {
                    break;
                }
                //若不为空，则添加该数据于ArrayList类型的column中。
                column.add(sheet.getRow(j).getCell(i).toString());
            }
            //遍历完一列后添加该列数据到result中
            result.add(column);
        }
        return result;
    }

//    ExcelReader(String filePath) {
//        this.filePath = filePath;
//    }

//    public static void main(String[] args) throws IOException {
//        FileInputStream fileInputStream = new FileInputStream(filePath);
//        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        XSSFRow row = null;
//        for (int i = 0; sheet.getRow(i) !=null ; i++) {
//            row = sheet.getRow(i);
//            for (int j = 0; row.getCell(j) !=null ; j++) {
//                System.out.println(row.getCell(j).toString());
//            }
//        }
//    }
}
