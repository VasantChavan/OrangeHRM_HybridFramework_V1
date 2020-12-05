package com.demoExtentReports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class OrangeHrmTest {

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports report;
	public ExtentTest test;
	public static WebDriver driver;

	@BeforeTest
	public void setupExtent() {

		File fs = new File("./Reports/orange_hrm.html");

		htmlReporter = new ExtentHtmlReporter(fs);

		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Functional Report");
		htmlReporter.config().setTheme(Theme.DARK);

		report = new ExtentReports();
		report.attachReporter(htmlReporter);

		report.setSystemInfo("HostName", "LocalHost");
		report.setSystemInfo("OS", "Windows");
		report.setSystemInfo("Tesrter Nane", "Vasant");
		report.setSystemInfo("Browser", "Chrome");

	}

	@AfterTest
	public void endReport() {
		report.flush();
	}

	@BeforeMethod
	public void setup() {

		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void verifyTitleTest() {

		test = report.createTest("verifyTitleTest");
		String actualTitle = driver.getTitle();
		String expectedTitle = "OrangeHRM";

		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test
	public void verifyLogoTest() {

		test = report.createTest("verifyLogoTest");
		boolean status = driver.findElement(By.xpath("//div[@id='divLogo']/img")).isDisplayed();
		Assert.assertFalse(status);
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case failed" + result.getName());
			test.log(Status.FAIL, "Test Case Failed" + result.getThrowable());

			String capturedScreen = captureScreenshot(result.getName());
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

	public static String captureScreenshot(String screenshotName) throws IOException {

		String dateFormat = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		String screenshotPath = System.getProperty("user.dir")+"/Screenshots/" + screenshotName + dateFormat + ".png";

		File destFile = new File(screenshotPath);
		FileHandler.copy(srcFile, destFile);

		return screenshotPath;
	}
}
