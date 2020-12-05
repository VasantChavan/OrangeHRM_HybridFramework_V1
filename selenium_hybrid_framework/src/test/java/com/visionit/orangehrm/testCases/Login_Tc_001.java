package com.visionit.orangehrm.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.visionit.orangehrm.pageObject.LoginPage;
import com.visionit.orangehrm.testBase.TestBase;

public class Login_Tc_001 extends TestBase {

	@Test
	public void loginOrangeHrmTest() {
		LoginPage login = new LoginPage(driver);
		// login.loginOrangeHrm(configData.getUserName(),
		// configData.getUserPassword());

		test = report.createTest("loginOrangeHrmTest");

		test.info("Navigated on login page");
		login.loginOrangeHrm(xlsxData.getStringCellData("login", 0, 0), xlsxData.getStringCellData("login", 0, 1));

		test.info("Enter valid username and password and then click on login button");

		String actualTitle = driver.getTitle();
		String expectedTitle = "";

		if (actualTitle.equals(expectedTitle)) {
			Assert.assertTrue(true);
			test.pass("Login page title is verified...");

		} else {
			test.fail("login page title not matched ...");
			Assert.assertTrue(false);
		}
	}

}
