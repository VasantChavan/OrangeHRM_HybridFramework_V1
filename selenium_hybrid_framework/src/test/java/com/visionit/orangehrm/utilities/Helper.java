package com.visionit.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;

public class Helper {

	
	// handle windows, iframe, alert, etc
	
	public static void selectDropDownValue(WebElement ele, String visibleText) {

		new Select(ele).selectByVisibleText(visibleText);
	}

	public static void selectDropDownValue(WebElement ele, int index) {

		new Select(ele).selectByIndex(index);
	}

	public static void selectDropDownValue(String value, WebElement ele) {

		new Select(ele).selectByValue(value);
	}
	
	public static String getcustomDate(){
		String dateFormat =  new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		return dateFormat;
	}
	public static String captureScreenshot(String screenshotName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		
		String screenshotPath = System.getProperty("user.dir")+"/Screenshots/" + screenshotName + getcustomDate() + ".png";

		File destFile = new File(screenshotPath);
		FileHandler.copy(srcFile, destFile);

		return screenshotPath;
	}

}
