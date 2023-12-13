package com.recipe.testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.recipe.testBase.BaseClass;

public class TC_002_HypoThyroidism extends BaseClass{
	
	public void hypoThyrodism()
	{
		WebElement clickOnAtoZ=driver.findElement(By.xpath("//div[@id='toplinks']/a[5]"));
		clickOnAtoZ.click();
		logger.info("clicked on Recipe A to Z");

}
}