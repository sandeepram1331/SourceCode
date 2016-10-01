package com.src.tennis.flashscore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.src.hibernateutil.HibernateUtil;
import com.src.tennis.flashscore.entity.MatchDetailsTomorrow;

public class Helper_To_Put_Details_DB {


	public void toDB(String fileLocation) throws IOException
	{

		//Need to find list of IDs for games
		//1)Write file to html
		//2)Use Jsoup to parse html and get Ids
		//3)Construct Url for in-progress Records

		/**********************************************************************************/
		/**********Write File to HTML Page**************************************************/
		/***********************************************************************************/


		/*WebDriver driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String baseUrl = "http://www.flashscore.com/tennis/";
			driver.get(baseUrl);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,1400)", "");
			Thread.sleep(3000);
			String html = driver.getPageSource();
			PrintWriter writer = new PrintWriter("D:\\TomorrowsGames.html", "UTF-8");
			writer.println(html);
			writer.close();
		 */
		/**********************************************************************************/
		/**********Use Jsoup to parse HTML and get IDs**************************************/
		/***********************************************************************************/
		File input = new File(fileLocation);
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Element tableDiv = doc.getElementById("fs");
		Elements es = tableDiv.getAllElements();
		List<String> matches =  new ArrayList<String>();;
		for(Element e : es)
		{
			String match = e.id();
			if(match.contains("x_2_"))
			{
				matches.add(match);
			}
		}
		List<String> matchids = new ArrayList<String>();
		if(matches.size()>0)
		{
			for(String matchname : matches)
			{
				matchids.add(matchname.substring(4));
			}
		}
		else{
			System.out.println("null");
		}

		for(String match:matchids)
		{
			//System.out.println("g_2_"+match);
			Element eleHome = doc.getElementById("g_2_"+match);
			Element eleAway = doc.getElementById("x_2_"+match);
			String ms1,ms2;
			String pas11,pas12,pas21,pas22;
			String player1,player2,matchStatus = null;
			ms1=eleHome.select("td[class=cell_aa timer ]").text();
			ms2=eleHome.select("td[class=cell_aa timer  playing ]").text();
			pas11= eleHome.select("td[class=cell_ab team-home ]").text();
			pas12= eleHome.select("td[class=cell_ab team-home  bold ]").text();
			pas21= eleAway.select("td[class=cell_ac team-away ]").text();
			pas22= eleAway.select("td[class=cell_ac team-away bold ]").text();
			//System.out.println(ms1+"-"+ms2+"-"+pas11+"-"+pas12+"-"+pas21+"-"+pas22);
			if(ms1.equals(" ")){
				System.out.println("Not Started");
				matchStatus="NotStarted";
			}
			else if(ms2.contains("Set"))
			{
				System.out.println("Playing");
				matchStatus="Playing";
			}
			else if(ms1.contains("Fin"))
			{
				System.out.println("Finished");
				matchStatus="Finished";
			}
			else if(ms1.contains("FRO"))
			{
				System.out.println("Final Result Only");
				matchStatus="FRO";
			}

			if(pas11.equals("")||pas11==null)
			{
				player1=pas12;
			}
			else{
				player1=pas11;
			}
			if(pas21.equals("")||pas21==null)
			{
				player2=pas22;
			}
			else{
				player2=pas21;
			}
			System.out.println("MatchStatus - "+matchStatus);
			System.out.println("Player1 - "+player1);
			System.out.println("Player2 - "+player2);
			Date dt = new Date();
			MatchDetailsTomorrow mdt = new MatchDetailsTomorrow();
			mdt.setPlayer1(player1);
			mdt.setPlayer2(player2);
			mdt.setMatchId(match);
			mdt.setDate(dt);
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(mdt);
			session.getTransaction().commit();
			session.close();




			//Save the record to DB

		}
		//Construct allIdsForDay and allInProgressMatches
		/**********************************************************************************/
		/**********Construct Url for in-progress Records***********************************/
		/***********************************************************************************/

		//Check the inprogress games and follow those  - insert into inprogress table

		//Check for completed and insert into DB and simultaneously both the players are present in DB or not.

		//if the players are not present in DB add the file to table where players data need to be taken. - unknown players

		//
	}

}

