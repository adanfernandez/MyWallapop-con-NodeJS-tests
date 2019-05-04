package com.uniovi.tests.pageobjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView {

	public static void fillForm(WebDriver driver, String emailp, String passwordp) {

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement email = driver.findElement(By.name("email"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		//Pulsar el boton de login.
		By boton = By.className("btn");
		driver.findElement(boton).click();
		
	}

}
