package com.visionit.orangehrm.testBase;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.visionit.orangehrm.utilities.ConfigDataProvider;
import com.visionit.orangehrm.utilities.Helper;
import com.visionit.orangehrm.utilities.XLSXDataProvider;

public class TestBase {

	public static WebDriver driver = null;
	public ConfigDataProvider configData;
	public XLSXDataProvider xlsxData;
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports report;
	public ExtentTest test;

	@BeforeSuite
	public void setupSuite() {
		Reporter.log("instantiating an config and xlsx data provider.", true);
		configData = new ConfigDataProvider();
		xlsxData = new XLSXDataProvider();
		Reporter.log("config and xlsx data provider is instantiated.", true);
		}
	@BeforeTest
	public void setupExtent(){
		
		File fs = new File("./Reports/orange_hrm_"+Helper.getcustomDate()+".html");

		htmlReporter = new ExtentHtmlReporter(fs);

		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Functional Report");
		htmlReporter.config().setTheme(Theme.STANDARD);

		report = new ExtentReports();
		report.attachReporter(htmlReporter);

		report.setSystemInfo("HostName", "LocalHost");
		report.setSystemInfo("OS", "Windows");
		report.setSystemInfo("Tester Nane", "Vasant");
		report.setSystemInfo("Browser", "Chrome");
	
	}
	
	@AfterTest
	public void endReport() {
		report.flush();
	}

	@Parameters("Browser")
	@BeforeMethod
	public void setUp(@Optional("Chrome") String browserName) {

		if (browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "./Driver/geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equals("IE")) {
			System.setProperty("webdriver.ie.driver", "./Driver/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		// driver.get("https://opensource-demo.orangehrmlive.com/");

		// driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		driver.get(configData.getAppUrl());

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case failed" + result.getName());
			test.log(Status.FAIL, "Test Case Failed" + result.getThrowable());

			String capturedScreen = Helper.captureScreenshot(result.getName(), driver);
			test.addScreenCaptureFromPath(capturedScreen);
			//test.fail("test case failed", MediaEntityBuilder.createScreenCaptureFromPath(capturedScreen).build());
		}
		else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case Passed" + result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case Skipeed" + result.getName());
		}

		driver.quit();
	}


}
