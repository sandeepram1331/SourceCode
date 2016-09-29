package com.src.tennis.flashscore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mkyong.persistence.HibernateUtil;
import com.src.tennis.flashscore.entity.WTAPlayer;
import com.src.tennis.flashscore.entity.PLAYER_MATCHID_LAST_FEW;
import com.src.tennis.flashscore.entity.WTAPlayerMatchIDMap;
import com.src.tennis.flashscore.entity.WTA_PLAYER_MATCHID_LAST_FEW;

public class WTAMatchesFromSavedPlayerProfile {


	/*
	 * 1: Get list user details from DB
	 * 2: Check the file is available for the user
	 * 3: Get the Id's into DB.
	 * 4: Construct the URL's and save to WTA/matchID folder
	 * */

	public static void main(String ar[]) throws IOException
	{

		//1: Get list user details from DB

		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(WTAPlayer.class);
		criteria.add(Restrictions.lt("rank", 2000));
		criteria.addOrder(Order.asc("rank"));
		System.out.println();
		ArrayList<WTAPlayer> alplayer =  (ArrayList<WTAPlayer>) criteria.list();

		//2:Check the file is available for the user
		boolean fileexist=false;
		for(WTAPlayer player:alplayer)
		{
			
			File f = new File("D://WTA//"+player.getRank()+"_"+player.getName()+".html");

			if(f.exists()){
				fileexist = true;
				System.out.println("D://WTA//"+player.getRank()+"_"+player.getName()+".html" + " - Exist");
			}else{
				fileexist = false;
				System.out.println("D://WTA//"+player.getRank()+"_"+player.getName()+".html" + " - Not Exist Creating");

			}
			Document doc = Jsoup.parse(f, "UTF-8", "http://example.com/");
			Element dataTable = doc.getElementById("fs-results");
			//System.out.println(dataTable.html());
			Elements es = null;
			if(dataTable!=null)
				es = dataTable.getAllElements();

			if(es!=null)
				for(int i=0,j=0;i<es.size()&&j<20;i++)
				{
					Element e = es.get(i);
					String match = e.id();
					if(match.contains("g_2_"))
					{
						try
						{
							j++;
						Session session1 = HibernateUtil.getSessionFactory().openSession();
						session1.beginTransaction();
						/*WTAPlayerMatchIDMap map = new WTAPlayerMatchIDMap();
						map.setMatchId(match.substring(4));
						map.setPlayername(player.getName());
						session1.save(map);
						session1.getTransaction().commit();
						session1.close();
						}
						catch(Exception e1)
						{
							continue;
						}*/
						WTA_PLAYER_MATCHID_LAST_FEW map = new WTA_PLAYER_MATCHID_LAST_FEW();
						map.setMatchId(match.substring(4));
						map.setPlayername(player.getName());
						session1.save(map);
						session1.getTransaction().commit();
						session1.close();
						}
						catch(Exception e1)
						{
							continue;
						}
						}
				}
		}

	}

}
