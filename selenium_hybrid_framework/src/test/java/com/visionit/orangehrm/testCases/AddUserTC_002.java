package com.visionit.orangehrm.testCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.visionit.orangehrm.pageObject.AddUserPage;
import com.visionit.orangehrm.pageObject.HomePage;
import com.visionit.orangehrm.pageObject.LoginPage;
import com.visionit.orangehrm.testBase.TestBase;

public class AddUserTC_002 extends TestBase {

	@Test(dataProvider = "getExcelData")
	public void addNewUserTest(String user_role, String empName, String userName, String status, String password,
			String confirmPassword) {

		LoginPage login = new LoginPage(driver);

		HomePage homepage = login.loginOrangeHrm(xlsxData.getStringCellData("login", 0, 0),
				xlsxData.getStringCellData("login", 0, 1));

		AddUserPage addUser = homepage.navigateToAddUserPage();

		addUser.addNewUser(user_role, empName, userName, status, password, confirmPassword);

	}

	@DataProvider
	public Object[][] getExcelData() {
		Object[][] data = xlsxData.excelTestData("adduser");
		return data;
	}
}
