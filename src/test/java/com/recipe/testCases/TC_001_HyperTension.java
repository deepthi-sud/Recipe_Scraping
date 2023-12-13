package com.recipe.testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.recipe.testBase.BaseClass;

public class TC_001_HyperTension extends BaseClass {


	@Test
	public void hypertension()
	{
		WebElement clickOnAtoZ=driver.findElement(By.xpath("//div[@id='toplinks']/a[5]"));
		clickOnAtoZ.click();
		logger.info("clicked on Recipe A to Z");
		
}
}
