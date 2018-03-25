package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_CreatePublicationView extends PO_NavView {

	static public void fillForm(WebDriver driver, String titulop, String textop) {
		WebElement name = driver.findElement(By.id("title"));
		name.click();
		name.clear();
		name.sendKeys(titulop);
		WebElement lastname = driver.findElement(By.id("texto"));
		lastname.click();
		lastname.clear();
		lastname.sendKeys(textop);
		// Pulsar el boton de Alta.
		By boton = By.id("publicar");
		driver.findElement(boton).click();
	}
}
