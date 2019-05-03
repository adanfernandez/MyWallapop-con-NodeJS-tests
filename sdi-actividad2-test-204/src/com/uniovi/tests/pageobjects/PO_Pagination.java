package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_Pagination {

	
	public static void selectPage(WebDriver driver, String position) {

		WebElement search = driver.findElement(By.id("next"));
		search.click();
	}
	
	
}
