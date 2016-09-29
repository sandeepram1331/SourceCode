//CPT - Copy to file - write to a file
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
import com.src.tennis.flashscore.entity.ATPPlayer;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class CTF_For_EachPlayer_Contain_All_Matches
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
		ArrayList<ATPPlayer> players = new ArrayList<ATPPlayer>();
		players = fetchUrls();
		Selenium selenium;
		driver = new FirefoxDriver();
		boolean fileexist=false;
		for(ATPPlayer player: players)
		{
			/*
			 * Check for the presence of page
			 * */
			File f = new File("D://ATP//"+player.getRank()+"_"+player.getName()+".html");

			if(f.exists()){
				fileexist = true;
				System.out.println("D://ATP//"+player.getRank()+"_"+player.getName()+".html" + " - Exist");
			}else{
				fileexist = false;
				System.out.println("D://ATP//"+player.getRank()+"_"+player.getName()+".html" + " - Not Exist Creating");
				
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
					File file = new File("D://ATP//"+player.getRank()+"_"+player.getName()+".html");
					//File file = new File("D://ATP//dfasd.txt");
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
	private static ArrayList<ATPPlayer> fetchUrls() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(ATPPlayer.class);
		criteria.add(Restrictions.lt("rank", 5000));
		criteria.addOrder(Order.asc("rank"));
		System.out.println();
		ArrayList<ATPPlayer> alplayer =  (ArrayList<ATPPlayer>) criteria.list();
		System.out.println(alplayer.size());
		return alplayer;

	}
}
