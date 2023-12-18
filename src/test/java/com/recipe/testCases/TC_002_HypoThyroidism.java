package com.recipe.testCases;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
//import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;

import com.recipe.testBase.BaseClass;
import com.recipe.utilities.ExcelReadAndWrite;

public class TC_002_HypoThyroidism extends BaseClass {

	static int flag = 0;
	int eliminated_recipe_count =  0;
	Instant timer_start, timer_end;

	public enum FoodCategory {
		VEGETARIAN , VEGAN,EGGETARIAN, JAIN

	}

	public enum RecipeCategory {
		NONE, BREAKFAST ,LUNCH, SNACK , DINNER
	}

	public enum Comorbidity {
		DIABETES , PCOS , HYPOTHYROIDISM , HYPERTENSION
	}

	@Test
	public  void hypoThyrodism() throws Exception

	{
		timer_start = Instant.now();
		int eliminated_recipe_count =  0;
		int total_recipes = 0;


		ArrayList<String> EliminatedList = ExcelReadAndWrite.readEliminatedList(2);


		//Click on Recipes A to Z
		WebElement recipes=driver.findElement(By.xpath("//div[@id = 'toplinks']/a[5]"));
		recipes.click();
		logger.info("clicked on Recipe A to Z");

		//click on alphabets
		WebElement alphab=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']//td[5]"));
		alphab.click();

		List <WebElement> recipe_link_pages = driver.findElements(By.xpath("//a[@class='respglink']"));
		int num =recipe_link_pages.size();
		System.out.println(num);
		String total_pages_to_scrape = recipe_link_pages.get(num-1).getText();
		int total_pages = (Integer.parseInt(total_pages_to_scrape));
		System.out.println("Total pages to Scrape: "+total_pages);

		//Traverse through pages

		for(int p=1; p<=5;p++) {
			String current_url = driver.getCurrentUrl();
			System.out.println(current_url);

			WebElement active_page=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a[text()='"+p+"']"));
			System.out.println("active page: "+active_page.getText());        
			active_page.click();

			List <WebElement> recipeLinks=driver.findElements(By.xpath("//div[@class='rcc_recipecard']//div[3]/span/a"));
			int recipeLinksSize=recipeLinks.size();

			System.out.println("Total Recipe cards in Page"+p+":"+recipeLinksSize);
			try {
				//Traverse through recipes in each page
				for_loop:
					for(int j=0;j<recipeLinksSize; j++ )
					{


						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("window.scrollBy(0,350)", "");


						String Recipe_name = recipeLinks.get(j).getText();

						recipeLinks.get(j).click();

						String ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();

						flag = 0;
						outer:
							for(int k=0;k<EliminatedList.size();k++)
							{
								String s1 =EliminatedList.get(k).toLowerCase();
								String s2 = ingredients.toLowerCase();
								if(s2.contains(s1))
								{
									eliminated_recipe_count++;
									flag = 1;
									break outer;
								}

							}

						if(flag==0)
						{
							System.out.println("********************************************************");
							System.out.println("Recipe name: " +Recipe_name);
							String recipe_url = driver.getCurrentUrl();
							String recipe_id = recipe_url.replaceAll("[^0-9]", "");
							System.out.println("Recipe ID: "+ recipe_id);
							System.out.println("****Ingredients****");

							System.out.println(ingredients);
							System.out.println("------------------------");
							String PrepTime = driver.findElement(By.xpath("//time[@itemprop = 'prepTime']")).getText();
							System.out.println("PreparationTime: "+PrepTime);
							String CookTime = driver.findElement(By.xpath("//time[@itemprop = 'cookTime']")).getText();
							System.out.println("CookingTime: "+CookTime);
							String tags = driver.findElement(By.xpath("//div[@id = 'recipe_tags']")).getText();
							System.out.println("------------------------");
							String nutrients="";
							try {
								nutrients = driver.findElement(By.xpath("//table[@id='rcpnutrients']")).getText();

							}catch( NotFoundException e) {
								nutrients=" nutrients list not found";
								logger.info(current_url, Recipe_name, ingredients, recipe_url, recipe_id, PrepTime, CookTime, tags, nutrients, e);
							}

							System.out.println("Nutrient List :"+nutrients);
							System.out.println("------------------------");
							String prepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']")).getText();
							System.out.println("Prepration Method :"+prepMethod);
							System.out.println("------------------------");
							System.out.println("FoodCategory: "+getFoodCategory().toString());
							System.out.println("------------------------");
							System.out.println("RecipeCategory: "+getRecipeCategory().toString());
							System.out.println("------------------------");
							System.out.println("TargettedMorbidity: "+SetCoMorbidity().toString());
						}
						driver.navigate().back();
						recipeLinks=driver.findElements(By.xpath("//div[@class='rcc_recipecard']//div[3]/span/a"));
					}
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Index Out of Bound Exception");
				driver.navigate().back();
				recipeLinks=driver.findElements(By.xpath("//div[@class='rcc_recipecard']//div[3]/span/a"));
				continue;
							}
			total_recipes = total_recipes + recipeLinksSize;
			System.out.println("Total Recipes:"+total_recipes);  
			System.out.println("Eliminated Recipes:"+eliminated_recipe_count);
			timer_end = Instant.now();
			System.out.println("Time taken to execute in Minutes: "+ Duration.between(timer_start,timer_end).toMinutes());;
		}

	}
	public FoodCategory getFoodCategory() {

		String tags = driver.findElement(By.xpath("//div[@id = 'recipe_tags']")).getText();
		String ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();

		if (tags.toLowerCase().contains("jain")) {
			return FoodCategory.JAIN;

		} else if (ingredients.toLowerCase().contains("egg")) {
			return FoodCategory.EGGETARIAN;
		}
		Pattern p= Pattern.compile("(milk|cheese|yogurt|icecream|buttermilk|paneer|ghee|chocolate|butter|dahi)");
		Matcher regexMatcher = p.matcher(ingredients.toLowerCase());
		if(!regexMatcher.find()) {
			return FoodCategory.VEGAN;                
		}
		return FoodCategory.VEGETARIAN;
	}

	public RecipeCategory getRecipeCategory() {
		String tags = driver.findElement(By.xpath("//div[@id = 'recipe_tags']")).getText();

		if (tags.toLowerCase().contains("breakfast")) {
			return RecipeCategory.BREAKFAST;

		} else if (tags.toLowerCase().contains("lunch")) {
			return RecipeCategory.LUNCH;

		} else if (tags.toLowerCase().contains("snack")) {
			return RecipeCategory.SNACK;
		} else if (tags.toLowerCase().contains("dinner")) {
			return RecipeCategory.DINNER;
		}
		return RecipeCategory.NONE;
	}

	public Comorbidity SetCoMorbidity() {

		return Comorbidity.HYPOTHYROIDISM;

	}	
}

