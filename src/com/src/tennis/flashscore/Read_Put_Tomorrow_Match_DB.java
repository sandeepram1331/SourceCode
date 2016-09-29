package com.src.tennis.flashscore;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.gargoylesoftware.htmlunit.javascript.host.file.File;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class Read_Put_Tomorrow_Match_DB {
	
	public static void main(String ar[]) throws IOException
	{
		Selenium selenium;
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://www.flashscore.com/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		selenium.open("/tennis/");
		selenium.waitForPageToLoad("160000");	
		selenium.click("css=span.inner > span.arrow");
		selenium.waitForPageToLoad("160000");
		System.out.println("load complete");
		Date today = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(today); 
		c.add(Calendar.DATE, 1);
		Date tomorrow = c.getTime();
		String foldername = tomorrow.getDate()+"-"+tomorrow.getMonth()+"-"+tomorrow.getYear();
		String fileNameTomorrow = "D:\\MatchesDate\\MatchesFile"+foldername+".html";
		UtilitiesClass ul = new UtilitiesClass();
		ul.createFile(fileNameTomorrow);
		FileWriter fw = new FileWriter(fileNameTomorrow);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(driver.getPageSource());
		System.out.println(driver.getPageSource());
		bw.close();
		Helper_To_Put_Details_DB tb= new Helper_To_Put_Details_DB();
		tb.toDB(fileNameTomorrow);
		

	}
}
