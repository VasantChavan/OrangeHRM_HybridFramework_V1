package com.visionit.orangehrm.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;

	// WebElement repository at page Level

	@FindBy(name = "txtUsername")
	WebElement username;

	@FindBy(name = "txtPassword")
	WebElement userpas;

	@FindBy(id = "btnLogin")
	WebElement loginBtn;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public HomePage loginOrangeHrm(String uname, String upass) {

		// username.sendKeys("Admin");
		// userpas.sendKeys("admin123");
		// loginBtn.click();

		username.sendKeys(uname);
		userpas.sendKeys(upass);
		loginBtn.click();
		
		return new HomePage(driver);
	}
}
