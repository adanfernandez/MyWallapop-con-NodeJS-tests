package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_SearchProducts {

	public static void fillForm(WebDriver driver, String searchp) {


		WebElement search = driver.findElement(By.name("busqueda"));
		search.click();
		search.clear();
		search.sendKeys(searchp);
		//Pulsar el boton de login.
		By boton = By.className("btn");
		driver.findElement(boton).click();
		
	}

}
