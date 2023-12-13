package com.recipe.testBase;

import java.time.Duration;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.annotations.Parameters;

import com.recipe.utilities.CreateExcel;

public class BaseClass {
	
	public static WebDriver driver;
	public static ResourceBundle rb;
	public Logger logger;
	public static CreateExcel ce; 
	@BeforeClass
	@Parameters("browser")
	public void setup(String br) throws Exception
	{
		rb=ResourceBundle.getBundle("config");
		logger=LogManager.getLogger(this.getClass()); 
		//ChromeOptions options=new ChromeOptions();
		if(br.equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
//			options.addArguments("--headless");
//			driver=new ChromeDriver(options);
		}
		else if(br.equalsIgnoreCase("edge"))
		{
			driver=new EdgeDriver();
		}
		else if(br.equalsIgnoreCase("firefox"))
				{
					driver=new FirefoxDriver();
				}
		else
		{
			throw new Exception("Browser is not correct");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(rb.getString("baseURL"));
		logger.info("read app url");

		driver.manage().window().maximize();
		logger.info("window maximized");
		}
	
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}

}
