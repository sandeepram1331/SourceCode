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

import com.src.entities.ATPPlayer;
import com.src.hibernateutil.AnnotationUtil;
import com.src.hibernateutil.HibernateUtil;

public class RTF_Rank_Player_Details {

	public static void main(String args[]) throws IOException
	{
		Session session1 = AnnotationUtil.giveSession();
		Session session = AnnotationUtil.giveSession();
		File input = new File("E:\\d_drive\\ATPRankings.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Element table = doc.getElementById("ranking-table-results_35851");
		Elements rowodd = table.select("tr[class=rank-row odd]");
		Elements roweven = table.select("tr[class=rank-row even]");
		System.out.println(rowodd.size());
		System.out.println(roweven.size());
		int i= rowodd.size();int j= roweven.size();
		int max = i>j? i: j;
		System.out.println(i+j);
		List<ATPPlayer> atpLi = new ArrayList<ATPPlayer>();
		List<String> urls = new ArrayList<String>();
		for(int k=0;k<max;k++)
		{
			ATPPlayer atp = new ATPPlayer();
			Element odd = rowodd.get(k);
			Elements tds = odd.getElementsByTag("td");
			String rank = odd.select("td[class=rank-column-rank]").text();
			System.out.println(rank);
			int rankint = Integer.parseInt(rank.substring(0, rank.length()-1));
			System.out.println(rankint);
			String name = odd.select("td[class=rank-column-player]").text();
			String nationality= tds.get(2).text();
			String rankColumnPoints = odd.select("td[class=rank-column-points]").text();			
			String rankColumnTournaments = odd.select("td[class=rank-column-tournaments]").text();
			//System.out.println(rankint+"-"+name+"-"+rankColumnPoints+"-"+rankColumnTournaments+"-"+nationality);
			atp.setName(name);
			atp.setRank(rankint);
			atp.setNationality(nationality);
			atp.setRankColumnPoints(Integer.parseInt(rankColumnPoints));
			atp.setRankColumnTournaments(Integer.parseInt(rankColumnTournaments));
			Element link = odd.select("a").first();
			String url = link.attr("abs:href");
			//baseUrl.substring(25,baseUrl.length())+"/"
			atp.setPlayerURL(url);
			atp.setPlayerEndURL(url.substring(25,url.length())+"/");
			String playerId = url.substring(url.length()-8, url.length());
			System.out.println(playerId+" - playerId");
			atp.setGender("male");
			atp.setPlayerId(playerId);
			atp.setDateAdded(new Date());
			
			session.beginTransaction();
			session.save(atp);
			session.getTransaction().commit();
			session.clear();
		//	atpLi.add(atp);
			//session.close();
			ATPPlayer atp2 = new ATPPlayer();
			if(k<=j-1)
			{
				Element even = roweven.get(k);
				Elements tds2 = even.getElementsByTag("td");
				String rank2 = even.select("td[class=rank-column-rank]").text();
				int rankint2 = Integer.parseInt(rank2.substring(0, rank2.length()-1));
				System.out.println(rank2+" -- "+rankint2);
				String name2 = even.select("td[class=rank-column-player]").text();
				String nationality2 = tds2.get(2).text();
				String rankColumnPoints2 = even.select("td[class=rank-column-points]").text();			
				String rankColumnTournaments2 = even.select("td[class=rank-column-tournaments]").text();
				atp2.setName(name2);
				atp2.setRank(rankint2);
				atp2.setNationality(nationality2);
				atp2.setRankColumnPoints(Integer.parseInt(rankColumnPoints2));
				atp2.setRankColumnTournaments(Integer.parseInt(rankColumnTournaments2));
				
				atp2.setGender("male");
				Element link1 = even.select("a").first();
				String url1 = link1.attr("abs:href");
				//baseUrl.substring(25,baseUrl.length())+"/"
				atp2.setPlayerURL(url1);
				atp2.setPlayerEndURL(url1.substring(25,url1.length())+"/");
				String playerId2 = url1.substring(url1.length()-8, url1.length());
				atp2.setPlayerId(playerId2);
				System.out.println(url1+" - "+playerId2);
					atp2.setDateAdded(new Date());
				
				session1.beginTransaction();
				session1.save(atp2);
				session1.getTransaction().commit();
				session1.clear();
				
				
			}			
		}
		//save player initial Details to DB

	}
}
