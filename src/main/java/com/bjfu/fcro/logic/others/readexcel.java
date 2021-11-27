package com.bjfu.fcro.logic.others;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readexcel {

    public static void main(String[] args) throws IOException {
        String excelPath = "E:\\文件\\laowang\\数据集\\liangxi\\7分甜（广益哥伦布店）.csv.xlsx";
        String sheetName = "7分甜（广益哥伦布店）";
        try{
            FileInputStream excelFile = new FileInputStream(excelPath);

            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet excelSheet = workbook.getSheet(sheetName);
            int rows = excelSheet.getPhysicalNumberOfRows(); //行数
            int cols = excelSheet.getRow(0).getPhysicalNumberOfCells();//列数

            for(int row = 0;row< rows; ++row){
                for (int col =0; col < cols; ++col){
                    System.out.print(ReadData(excelSheet, row, col) + ' ');
                    if(col ==1)
                        System.out.println();
                }
            }
            workbook.close();
        }catch (FileNotFoundException e ){
            System.out.println("找不到该文件");
        }
    }

    public static String ReadData(XSSFSheet excelSheet, int row, int col){
        try{
            String CellData= "";
            XSSFCell cell = excelSheet.getRow(row).getCell(col);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            CellData = cell.getStringCellValue();
            String utf8Str = new String(CellData.getBytes(StandardCharsets.UTF_8), "utf-8");
            return utf8Str;
        }catch(Exception e){
            return "";
        }
    }
}
