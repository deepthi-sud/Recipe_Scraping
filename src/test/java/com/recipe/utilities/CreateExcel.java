package com.recipe.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateExcel {
	
	static String path  = ".\\TestData\\Team7_Scraping_Spatulas";	  //need to confirm path
	public static void createExcel() throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet("Pcos_Recipes");
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 15);
		font.setBold(true);
		style.setFont(font);
		style.setFillPattern(FillPatternType.FINE_DOTS);
		style.setFillBackgroundColor((IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()));
		Row row = worksheet.createRow(0);
		
		row.createCell(0).setCellValue("Recipe Id");
		row.createCell(1).setCellValue("Recipe Name");
		row.createCell(2).setCellValue("Recipe Category");
		row.createCell(3).setCellValue("Food Category");
		row.createCell(4).setCellValue("Ingredients List");
		row.createCell(5).setCellValue("Preparation Time");
		row.createCell(6).setCellValue("Cooking Time");
		row.createCell(7).setCellValue("Preparation Method");
		row.createCell(8).setCellValue("Nutrient Values");
		row.createCell(9).setCellValue("Recipe URL");
		row.createCell(10).setCellValue("Targetted Morbid Conditions");
		for(int j = 0; j<=10; j++)
		{
		 row.getCell(j).setCellStyle(style);
			
		}
		XSSFSheet newsheet = workbook.cloneSheet(0,"Diabetes_recipes");
	    newsheet = workbook.cloneSheet(0,"Hypertension_recipes");
		newsheet = workbook.cloneSheet(0,"Hypothyroidism_recipes");
		XSSFSheet newsheet1 = workbook.createSheet("Allergy_info");
		Row row1 = newsheet1.createRow(0);
		
		row1.createCell(0).setCellValue("Morbid Condition");
		row1.createCell(1).setCellValue("Allergy Information");
		row1.createCell(2).setCellValue("Recipe Name");
		row1.createCell(3).setCellValue("Recipe URL");
		
		for(int j = 0; j<=3; j++)
		{
		 row1.getCell(j).setCellStyle(style);
			
		}
		
       	File excelFile = new File(path+"Team7_Scraping_Spatulas_Scraped_Recipies.xlsx");
		FileOutputStream fos = new FileOutputStream(excelFile);
		workbook.write(fos);
				
		workbook.close();
		fos.close();
	}
}
