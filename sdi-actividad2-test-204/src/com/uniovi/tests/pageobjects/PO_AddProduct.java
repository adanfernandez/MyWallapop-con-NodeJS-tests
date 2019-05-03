package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AddProduct {

	public static void fillForm(WebDriver driver, String titlep, String descriptionp, String pricep) {

		WebElement title = driver.findElement(By.name("nombre"));
		title.click();
		title.clear();
		title.sendKeys(titlep);
		WebElement description = driver.findElement(By.name("descripcion"));
		description.click();
		description.clear();
		description.sendKeys(descriptionp);
		WebElement price = driver.findElement(By.name("precio"));
		price.click();
		price.clear();
		price.sendKeys(pricep);
		//Pulsar el boton de login.
		By boton = By.name("añadir");
		driver.findElement(boton).click();
		
	}

}
