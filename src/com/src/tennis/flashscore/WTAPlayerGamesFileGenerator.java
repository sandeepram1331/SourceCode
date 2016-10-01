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

import com.src.hibernateutil.HibernateUtil;
import com.src.tennis.flashscore.entity.WTAPlayer;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class WTAPlayerGamesFileGenerator
{

	static WebDriver driver = null;
	
	public static void main(String args[]) throws IOException
	{
		/*
		 * 1: Connect to player database
		 * 2: Fetch URL's
		 * 3: Check for the presence of page
		 * 4: Create file with name and put data into that
		 * 
		 * */
		ArrayList<WTAPlayer> players = new ArrayList<WTAPlayer>();
		players = fetchUrls();
		Selenium selenium;
		driver = new FirefoxDriver();
		boolean fileexist=false;
		for(WTAPlayer player: players)
		{
			/*
			 * Check for the presence of page
			 * */
			File f = new File("D://WTA//"+player.getRank()+"_"+player.getName()+".html");

			if(f.exists()){
				fileexist = true;
				System.out.println("D://WTA//"+player.getRank()+"_"+player.getName()+".html" + " - Exist");
			}else{
				fileexist = false;
				System.out.println("D://WTA//"+player.getRank()+"_"+player.getName()+".html" + " - Not Exist Creating");
				
			}
			if(!fileexist)
			{
				try
				{
					String baseUrl=player.getPlayerURL();
					System.out.println(baseUrl);
					selenium = new WebDriverBackedSelenium(driver, "http://www.flashscore.com/");
					System.out.println(baseUrl.substring(25,baseUrl.length()));
					selenium.open(baseUrl.substring(25,baseUrl.length())+"/");
					selenium.click("link=Show more matches");
					selenium.waitForPageToLoad("30000");
					for(int i=0;i<20;i++)
					{
						selenium.click("link=Show more matches");
						selenium.waitForPageToLoad("30000");
					}
				}
				catch(Exception e)
				{
					File file = new File("D://WTA//"+player.getRank()+"_"+player.getName()+".html");
					//File file = new File("D://WTA//dfasd.txt");
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(driver.getPageSource());
					bw.close();			

				}
			}

		}
	}
	private static ArrayList<WTAPlayer> fetchUrls() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(WTAPlayer.class);
		criteria.add(Restrictions.lt("rank", 5000));
		criteria.addOrder(Order.asc("rank"));
		System.out.println();
		ArrayList<WTAPlayer> alplayer =  (ArrayList<WTAPlayer>) criteria.list();
		System.out.println(alplayer.size());
		return alplayer;

	}
}
