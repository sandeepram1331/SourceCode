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

import com.src.entities.ATPPlayer;
import com.src.hibernateutil.HibernateUtil;
import com.src.tennis.flashscore.entity.PLAYER_MATCHID_LAST_FEW;

public class RTF_Player_Pages_For_Match_Details {

	/*
	 * 1: Get list user details from DB
	 * 2: Check the file is available for the user
	 * 3: Get the Id's into DB.
	 * 4: Construct the URL's and save to ATP/matchID folder
	 * */

	public static void main(String ar[]) throws IOException
	{

		//1: Get list user details from DB

		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(ATPPlayer.class);
		criteria.add(Restrictions.lt("rank", 20));
		criteria.addOrder(Order.asc("rank"));
		System.out.println();
		ArrayList<ATPPlayer> alplayer =  (ArrayList<ATPPlayer>) criteria.list();

		//2:Check the file is available for the user
		boolean fileexist=false;
		for(ATPPlayer player:alplayer)
		{
			File f = new File("E://d_drive//ATP//"+player.getRank()+"_"+player.getName()+".html");

			if(f.exists()){
				fileexist = true;
				System.out.println("E://d_drive//ATP//"+player.getRank()+"_"+player.getName()+".html" + " - Exist");
			}else{
				fileexist = false;
				System.out.println("E://d_drive//ATP//"+player.getRank()+"_"+player.getName()+".html" + " - Not Exist Creating");

			}
			Document doc = Jsoup.parse(f, "UTF-8", "http://example.com/");
			Element dataTable = doc.getElementById("fs-results");
			//System.out.println(dataTable.html());
			Elements es = null;
			if(dataTable!=null)
				es = dataTable.getAllElements();

			if(es!=null)
				for(int i=0;i<es.size();i++)
				{
					Element e = es.get(i);
					String match = e.id();
					if(match.contains("g_2_"))
					{
						try
						{
						Session session1 = HibernateUtil.getSessionFactory().openSession();
						session1.beginTransaction();
						/*PlayerMatchIDMap map = new PlayerMatchIDMap();
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
						PLAYER_MATCHID_LAST_FEW map = new PLAYER_MATCHID_LAST_FEW();
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
