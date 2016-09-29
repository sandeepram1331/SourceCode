
package com.src.tennis.flashscore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mkyong.persistence.HibernateUtil;
import com.src.tennis.flashscore.entity.ATPPlayer;
import com.src.tennis.flashscore.entity.PLAYER_MATCHID_LAST_FEW;
import com.src.tennis.flashscore.entity.PlayerMatchIDMap;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class CTF_for_Sets_Matchdetails {
	private static Selenium selenium;
	public static void main(String a[]) throws IOException
	{
		/*
		 * 1: Get webpage for particular ID
		 * 2: Create Folder - 
		 *    Create Filematchprofile.html
		 * 3: Create File - odds.html
		 * 4: Create File - set1.html
		 * 5: Create File - set2.html
		 * 6: Create File - set3.html
		 * 7: Create File - set4.html
		 * 8: Create File - set5.html
		 * 9: Create file with name and put data into that
		 * 
		 * */

		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(PLAYER_MATCHID_LAST_FEW.class);
		ArrayList<PLAYER_MATCHID_LAST_FEW> fewmatches =  (ArrayList<PLAYER_MATCHID_LAST_FEW>) criteria.list();
		System.out.println(fewmatches.size()+"fewmatches.size");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);  
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		for(PLAYER_MATCHID_LAST_FEW p : fewmatches)
		{		
			newMethod(p);
		}	
	}

	private static boolean checkfolder(String matchId) {
		// TODO Auto-generated method stub
		String url = "D:\\ATP\\Matches\\"+matchId;
		File file = new File(url);
		if (!file.exists()) 
		{
			return false;
		}
		else{
			return true;
		}
	}

	static void createFile(String data, String id, String nameoffile) throws IOException
	{

		String url = "D:\\ATP\\Matches\\"+id+"";
		File file2 = new File(url+"\\"+nameoffile+".html");
		System.out.println(url);
		File file = new File(url);
		//File file = new File("D://ATP//dfasd.txt");
		// if file doesn't exists, then create it
		if (!file.exists()) 
		{
			if(file.mkdirs()){
				System.out.println("FileCreated");

				if(!file2.exists())
				{
					file2.createNewFile();
				}

			}
		}
		FileWriter fw = new FileWriter(file2.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(data);
		bw.close();	

	}

	void createDeletedFile(String matchid) throws IOException
	{
		System.out.println("Create deleted files");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(PlayerMatchIDMap.class);
		criteria.add(Restrictions.eq("matchId", matchid));
		ArrayList<PlayerMatchIDMap> fewmatches =  (ArrayList<PlayerMatchIDMap>) criteria.list();
		System.out.println(fewmatches.size());
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);  
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		//	WebDriver driver = new PhantomJSDriver(caps);              
		WebDriver driver = new FirefoxDriver();
		String id = matchid;
		try{
			String baseUrl = "http://www.flashscore.com/";
			selenium = new WebDriverBackedSelenium(driver, baseUrl);
			selenium.open("/match/"+id+"/#match-summary");
			selenium.waitForPageToLoad("30000");

			String matchsummary = driver.getPageSource();
			createFile(matchsummary,id,"matchsummary");
			String title = driver.getTitle();
			selenium.click("id=a-match-history");
			selenium.waitForPageToLoad("30000");
			String set1 = driver.getPageSource();
			createFile(set1,id,"set1");
			selenium.click("link=Set 2");
			selenium.waitForPageToLoad("30000");
			String set2 = driver.getPageSource();
			createFile(set2,id,"set2");
			selenium.click("link=Set 3");
			selenium.waitForPageToLoad("30000");
			String set3 = driver.getPageSource();
			createFile(set3,id,"set3");
			selenium.click("link=Set 4");
			selenium.waitForPageToLoad("30000");
			String set4 = driver.getPageSource();
			createFile(set4,id,"set4");
			selenium.click("link=Set 5");
			selenium.waitForPageToLoad("30000");
			String set5 = driver.getPageSource();
			createFile(set5,id,"set5");
			selenium.click("id=a-match-odds-comparison");
			selenium.waitForPageToLoad("30000");
			String odds = driver.getPageSource();
			createFile(odds,id,"odds_comparision");
			selenium.close();
		}
		catch(Exception e)
		{
			System.out.println("Limited Sets");
		}
		finally{
			selenium.close();
		}
	}
	static void newMethod(PLAYER_MATCHID_LAST_FEW p) throws IOException
	{

		String folder = "D:\\ATP\\Matches\\"+p.getMatchId();
		File f = new File(folder);
		if(f.isDirectory())
		{
			f.delete();
		}
		if(p.getMatchId().contains("myg"))
		{
			return;
		}

		if(checkfolder(p.getMatchId()))
		{
			return;
		}

		try
		{
			//WebDriver driver = new PhantomJSDriver(caps);              
			WebDriver driver = new FirefoxDriver();
			String id = p.getMatchId();
			String baseUrl = "http://www.flashscore.com/";
			selenium = new WebDriverBackedSelenium(driver, baseUrl);
			selenium.open("/match/"+id+"/#match-summary");
			selenium.waitForPageToLoad("30000");
			String matchsummary = driver.getPageSource();
			createFile(matchsummary,id,"matchsummary");
			try{
				String title = driver.getTitle();
				selenium.click("id=a-match-history");
				selenium.waitForPageToLoad("30000");
				String set1 = driver.getPageSource();
				createFile(set1,id,"set1");
				selenium.click("link=Set 2");
				selenium.waitForPageToLoad("30000");
				String set2 = driver.getPageSource();
				createFile(set2,id,"set2");
				selenium.click("link=Set 3");
				selenium.waitForPageToLoad("30000");
				String set3 = driver.getPageSource();
				createFile(set3,id,"set3");
				selenium.click("link=Set 4");
				selenium.waitForPageToLoad("30000");
				String set4 = driver.getPageSource();
				createFile(set4,id,"set4");
				selenium.click("link=Set 5");
				selenium.waitForPageToLoad("30000");
				String set5 = driver.getPageSource();
				createFile(set5,id,"set5");
			}
			catch(Exception e)
			{
				System.out.println("Limited Sets");
			}
			selenium.click("id=a-match-odds-comparison");
			selenium.waitForPageToLoad("30000");
			String odds = driver.getPageSource();
			createFile(odds,id,"odds_comparision");
			selenium.close();
		}
		catch(SeleniumException e){
			
			newMethod(p);
		}
	}
}
