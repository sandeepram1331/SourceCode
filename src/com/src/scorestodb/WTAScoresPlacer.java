//RTF - Read to file - Read from file
package com.src.scorestodb;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.src.entities.Failedmatchids;
import com.src.entities.MatchEntry_Old;
import com.src.entities.MatchSummary;
import com.src.entities.Matchentry;
import com.src.entities.PlayerRankHolder;
import com.src.entities.WTAPlayerMatchIDMap;
import com.src.hibernateutil.HibernateUtil;

public class WTAScoresPlacer implements Runnable{
	static int actualGameNumber = 0;
	List<WTAPlayerMatchIDMap> fewmatches = new ArrayList<WTAPlayerMatchIDMap>();

	WTAScoresPlacer(List<WTAPlayerMatchIDMap> list)
	{
		fewmatches=list;
	}

	public  void run()
	{
		//System.out.println(fewsetmatches.size()+" - few matches size");
		int total=0;
		System.out.println(fewmatches.size()+" - size");
		for(WTAPlayerMatchIDMap p : fewmatches)
		{
			//System.out.println(p.getMatchId() + " - matchid");
			//System.out.println(p.getMatchId());
			/*if(!p.getMatchId().contains("zDLI1Fa6"))
			{
				continue;
			}*/
			Session session2 = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria2 = session2.createCriteria(MatchSummary.class);
			criteria2.add(Restrictions.eq("matchid", p.getMatchId()));
			ArrayList<MatchSummary> alms = (ArrayList<MatchSummary>) criteria2.list();
			if(alms!=null  && alms.size()>0)
			{
				System.out.println("Match already exist in db");
				continue;
			}
			actualGameNumber = 0;
			try{
				//System.out.println(p.getMatchId()+"- First Line");
				if(p.getMatchId().contains("myg"))
				{
					continue;
				}
				PlayerRankHolder prh = matchSummaryProcessor(p);
				System.out.println(p.getMatchId());
				String player1=prh.player1;
				String player2 = prh.player2;
				int rank1=prh.rank1;
				int rank2 = prh.rank2;
				MatchSummary ms = new MatchSummary();
				ms.setPlayer1(player1);
				ms.setPlayer2(player2);
				ms.setPlayer1Id(prh.player1_id);
				ms.setPlayer2Id(prh.player2_id);
				ms.setRank1(rank1);
				ms.setRank2(rank2);
				ms.setMatchid(p.getMatchId());
				ms.setOddPlayer1(prh.odd1);
				ms.setOddPlayer2(prh.odd2);
				ms.setWon(prh.won);
				ms.setCountOfSets(prh.countOfSets);
				ms.setCountOfSetsInGame(prh.countOfSetsInGame);
				ms.setGender("female");
				ms.setMatchdate(prh.matchdate);
				System.out.println(prh.matchdate);
				if(ms.getWon()==2)
				{
					ms.setWinnerName(player2);
				}
				else
				{
					ms.setWinnerName(player1);
				}
				if(ms.getRank1()==0 && ms.getRank2()==0)
				{
					ms.setBetterRankPlayer("None");
				}
				if(ms.getRank1()>ms.getRank2() )
				{
					if(ms.getRank2()==0)
					{
						ms.setBetterRankPlayer(ms.getPlayer1());
					}
					else
					{
						ms.setBetterRankPlayer(ms.getPlayer2());
					}
				}
				else if(ms.getRank1()<ms.getRank2())
				{
					if(ms.getRank1()==0 )
					{
						ms.setBetterRankPlayer(ms.getPlayer2());
					}
					else
					{
						ms.setBetterRankPlayer(ms.getPlayer1());
					}
				}
				//System.out.println(prh.toString());
				ms.setOriginaldate(prh.gamedate);

				if(prh.countOfSets == null)
				{
					Session s = HibernateUtil.getSessionFactory().openSession();
					Criteria c = s.createCriteria(Failedmatchids.class);
					c.add(Restrictions.eq("matchid", p.getMatchId()));
					if(c.list().isEmpty())
					{
						Failedmatchids fms = new Failedmatchids();
						fms.setMatchid(p.getMatchId());
						Session s1 = HibernateUtil.getSessionFactory().openSession();
						s1.save(fms);
					}					
				}
				switch(prh.countOfSets)
				{
				case 5: ms.setSet5(prh.setscors[4]);
				case 4: ms.setSet4(prh.setscors[3]);
				case 3: ms.setSet3(prh.setscors[2]);
				case 2: ms.setSet2(prh.setscors[1]);
				case 1: ms.setSet1(prh.setscors[0]);
				default: break;
				}
				Session session1 = HibernateUtil.getSessionFactory().openSession();
				session1.beginTransaction();
				session1.save(ms);
				session1.getTransaction().commit();
				if(checkFile(p.getMatchId(),"set1"))
				{
					setprocessor(p,"set1", player1,  player2,  rank1,  rank2,1);
				}
				if(checkFile(p.getMatchId(),"set2"))
				{
					setprocessor(p,"set2", player1,  player2,  rank1,  rank2,2);
				}
				if(checkFile(p.getMatchId(),"set3"))
				{
					setprocessor(p,"set3", player1,  player2,  rank1,  rank2,3);
				}
				if(checkFile(p.getMatchId(),"set4"))
				{
					setprocessor(p,"set4", player1,  player2,  rank1,  rank2,4);
				}
				if(checkFile(p.getMatchId(),"set5"))
				{
					setprocessor(p,"set5", player1,  player2,  rank1,  rank2,5);
				}
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("---------------------------------------------------------------------------------------");
				System.out.println("Exception");e.printStackTrace();
				System.out.println("---------------------------------------------------------------------------------------");
				System.out.println(p.getMatchId());
				String url = "D:\\WTA\\Matches\\"+p.getMatchId();
				File file = new File(url);
				System.out.println("file exists - one");
				if (file.exists()) 
				{
					System.out.println("file exists");
					file.delete();
				}
				System.out.println("file exists - two");
				Session s = HibernateUtil.getSessionFactory().openSession();
				Criteria c = s.createCriteria(Failedmatchids.class);
				c.add(Restrictions.eq("matchid", p.getMatchId()));
				if(c.list().isEmpty())
				{
					Failedmatchids fms = new Failedmatchids();
					fms.setMatchid(p.getMatchId());
					Session s1 = HibernateUtil.getSessionFactory().openSession();
					s1.save(fms);
				}
				continue;
			}
			catch(Exception e)
			{
			}
		}
		System.out.println("---------------------------------------------------------------------------------------");
	}
	private static void setprocessor(WTAPlayerMatchIDMap p, String set, String player1, String player2, int rank1, int rank2, int tab ) throws IOException
	{
		int gameNumberProcessorOdd = 1;
		int gameNumberProcessorEven = 2;
		System.out.println("set processor - "+set);
		//System.out.println("D:\\WTA\\Matches\\"+p.getMatchId()+"\\"+set+".html");
		File input = new File("D:\\WTA\\Matches\\"+p.getMatchId()+"\\"+set+".html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Element e = doc.getElementById("tab-mhistory-"+tab+"-history");
		Elements all = doc.getAllElements();
		//System.out.println();
		Elements oddheads = e.select("tr[class=odd fifteens_available]");
		Elements oddscores = e.select("tr[class=odd fifteen]");
		if(oddscores==null)
		{
			//input.delete();
			//CTF_for_Sets_Matchdetails apm = new CTF_for_Sets_Matchdetails();
			//apm.createDeletedFile(p.getMatchId());
			setprocessor(p, set, player1, player2, rank1, rank2, tab);
		}
		Elements evenheads = e.select("tr[class=even fifteens_available]");
		Elements evenscores = e.select("tr[class=even fifteen]");
		//System.out.println(oddheads.size());
		Element oddhead = oddheads.get(0);
		//System.out.println(oddhead.html());
		int i=0;
		Elements tds = oddhead.select("td[class=server]");
		Element secondtd = tds.get(0);
		Elements divs = secondtd.select("div[class=icon-box]");
		if(divs.size()>0)
		{	i=1;
		}
		else
		{
			i=2;
		}
		for(int z=0;z<oddscores.size();z++)
		{
			//System.out.println(oddheads.get(z).text());

			MatchEntry_Old me = new MatchEntry_Old();
			me.setPlayer1(player1);
			me.setPlayer2(player2);
			me.setMatchid(p.getMatchId());
			me.setRank1(rank1);
			me.setRank2(rank2);
			Elements lostserve = oddheads.get(z).select("td[class=match-history-vertical fr lostserve]");
			/*System.out.println("sandeep - 1");
			System.out.println(lostserve.html());
			System.out.println("sandeep - 2");
			 */if(i==2)
			 {
				 me.setServernumber(2);
			 }
			 else
			 {
				 me.setServernumber(1);
			 }
			 //we need to parse the score and put into DB
			 me.setPoint1(oddscores.get(z).text());
			 me.setSetid(set);
			 me.setGamenumber(z*2+1);

			 /* System.out.println("sandeep - 1");
			 System.out.println(me.getBreakserve());
			 System.out.println(oddheads.get(z).html());
			 System.out.println(oddscores.get(z).text());
			 System.out.println("sandeep - 2");
			  */ if(oddheads.get(z).html().contains("LOST SERVE"))
			  {
				  // System.out.println("serve breaking avaialable");
				  me.setBreakserve(1);
			  }
			  else
			  {
				  me.setBreakserve(0);
			  }

			  Matchentry menew = new Matchentry();
			  menew.setBreakserve(me.getBreakserve());
			  menew.setActualgamenumber(actualGameNumber+z*2+1);
			  menew.setMatchid(p.getMatchId());
			  menew.setGamenumber(z*2+1);
			  menew.setScore(oddscores.get(z).text());
			  menew.setServeNumber(me.getServernumber());
			  menew.setSetid(set);
			  menew.setRank1(rank1);
			  menew.setRank2(rank2);
			  menew.setGender("female");

			  /* Place match data point got into DB*/
			  //score is in oddscores.get(z).text()

			  String score = oddscores.get(z).text();
			//  System.out.println(score);
			  String[] noofpoints  = score.split(",");
			//  System.out.println("------------------------");
			  int noofpointsplayed = noofpoints.length+1; //last point will be added to winner
			  if(noofpointsplayed<=6)
			  {
				  System.out.println("Less than 6");
				  if(menew.getBreakserve()==0)
				  {
					  if( menew.getServeNumber()==1)
					  {
						  System.out.println("1");
						  menew.setTotalpointswon_1(4);
						  menew.setTotalpointswon_2(noofpointsplayed-4);
					  }
					  else if(menew.getServeNumber()==2)
					  {
						  System.out.println("2");
						  menew.setTotalpointswon_2(4);
						  menew.setTotalpointswon_1(noofpointsplayed-4);
					  }
				  }
				  else
				  {
					  if( menew.getServeNumber()==1)
					  {
						  System.out.println("3");
						  menew.setTotalpointswon_2(4);
						  menew.setTotalpointswon_1(noofpointsplayed-4);
					  }
					  else if(menew.getServeNumber()==2)
					  {
						  System.out.println("4");
						  menew.setTotalpointswon_1(4);
						  menew.setTotalpointswon_2(noofpointsplayed-4);
					  }
				  }
			  }
			  else
			  {
				  System.out.println("5");
				  menew.setTotalpointswon_1(3);
				  menew.setTotalpointswon_2(3);
				  boolean adpre = score.contains("A");
				  if(adpre)
				  {
					  if(menew.getBreakserve()==0)
					  {
						  if(menew.getServeNumber()==1)
						  {
							  System.out.println("6");
							  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+2);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("7");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
						  else
						  {
							  System.out.println("8");
							  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+2);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("9");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
					  }
					  else
					  {
						  if(menew.getServeNumber()==1)
						  {
							  System.out.println("10");
							  menew.setTotalpointswon_2(menew.getTotalpointswon_1()+2);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("11");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
						  else
						  {
							  System.out.println("12");
							  menew.setTotalpointswon_1(menew.getTotalpointswon_2()+2);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("13");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
					  }
				  }
				  else
				  {

					  if(menew.getBreakserve()==0)
					  {
						  if(menew.getServeNumber()==1)
						  {
							  System.out.println("14");
							  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+1);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("15");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
						  else
						  {
							  System.out.println("16");
							  menew.setTotalpointswon_1(menew.getTotalpointswon_2()+1);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("17");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
					  }
					  else
					  {
						  if(menew.getServeNumber()==1)
						  {
							  System.out.println("17");
							  menew.setTotalpointswon_2(menew.getTotalpointswon_1()+1);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("18");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
						  else
						  {
							  System.out.println("19");
							  menew.setTotalpointswon_1(menew.getTotalpointswon_2()+1);
							  int remaining = noofpointsplayed-8;
							  if(remaining>0)
							  {
								  System.out.println("20");
								  menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								  menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							  }
						  }
					  }

				  }
			  }
			/*  for(int m=0;m<noofpoints.length;m++)
			  {
				  System.out.println(noofpoints[m]);
			  }
			*/  System.out.println("------------------------");

			  //15:0, 30:0, 40:0BP, 40:15BP


			  Session session1 = HibernateUtil.getSessionFactory().openSession();
			  session1.beginTransaction();
			  session1.save(menew);
			  session1.getTransaction().commit();
			  session1.close();
		}
		//System.out.println("second");
		for(int z=0;z<evenheads.size();z++)
		{

			MatchEntry_Old me = new MatchEntry_Old();
			me.setPlayer1(player1);
			me.setPlayer2(player2);
			me.setMatchid(p.getMatchId());
			me.setRank1(rank1);
			me.setRank2(rank2);
			Elements lostserve = evenheads.get(z).select("td[class=match-history-vertical fl lostserve]");
			if(evenheads.get(z).html().contains("LOST SERVE"))
			{
				//System.out.println("serve breaking avaialable");
				me.setBreakserve(1);
			}
			else
			{
				me.setBreakserve(0);
			}
			if(i==2)
			{
				me.setServernumber(1);
			}
			else
			{
				me.setServernumber(2);
			}
			//we need to parse the score and put into DB
			me.setPoint1(evenscores.get(z).text());
			me.setSetid(set);
			me.setGamenumber(z*2+2);
			/*	Session session1 = HibernateUtil.getSessionFactory().openSession();
			session1.beginTransaction();
			session1.save(me);
			session1.getTransaction().commit();
			 */	//System.out.println("Commited");
			Matchentry menew = new Matchentry();
			menew.setBreakserve(me.getBreakserve());
			menew.setActualgamenumber(actualGameNumber+z*2+2);
			menew.setMatchid(p.getMatchId());
			menew.setGamenumber(z*2+2);
			menew.setScore(evenscores.get(z).text());
			menew.setServeNumber(me.getServernumber());
			menew.setSetid(me.getSetid());
			menew.setRank1(rank1);
			menew.setRank2(rank2);
			menew.setGender("female");
			String score = menew.getScore();
			String[] noofpoints  = score.split(",");
			// System.out.println("------------------------");
			int noofpointsplayed = noofpoints.length+1; //last point will be added to winner

			if(noofpointsplayed<=6)
			{
				if(menew.getBreakserve()==0)
				{
					if( menew.getServeNumber()==1)
					{
						menew.setTotalpointswon_1(4);
						menew.setTotalpointswon_2(noofpointsplayed-4);
					}
					else if(menew.getServeNumber()==2)
					{
						menew.setTotalpointswon_2(4);
						menew.setTotalpointswon_1(noofpointsplayed-4);
					}
				}
				else
				{
					if( menew.getServeNumber()==1)
					{
						menew.setTotalpointswon_2(4);
						menew.setTotalpointswon_1(noofpointsplayed-4);
					}
					else if(menew.getServeNumber()==2)
					{
						menew.setTotalpointswon_1(4);
						menew.setTotalpointswon_2(noofpointsplayed-4);
					}
				}
			}
			else
			{
				menew.setTotalpointswon_1(3);
				menew.setTotalpointswon_2(3);
				boolean adpre = score.contains("A");
				if(adpre)
				{
					if(menew.getBreakserve()==0)
					{
						if(menew.getServeNumber()==1)
						{
							menew.setTotalpointswon_1(menew.getTotalpointswon_1()+2);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
						else
						{
							menew.setTotalpointswon_1(menew.getTotalpointswon_2()+2);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
					}
					else
					{
						if(menew.getServeNumber()==1)
						{
							menew.setTotalpointswon_2(menew.getTotalpointswon_1()+2);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
						else
						{
							menew.setTotalpointswon_1(menew.getTotalpointswon_2()+2);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
					}
				}
				else
				{

					if(menew.getBreakserve()==0)
					{
						if(menew.getServeNumber()==1)
						{
							menew.setTotalpointswon_1(menew.getTotalpointswon_1()+1);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
						else
						{
							menew.setTotalpointswon_1(menew.getTotalpointswon_2()+1);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
					}
					else
					{
						if(menew.getServeNumber()==1)
						{
							menew.setTotalpointswon_2(menew.getTotalpointswon_1()+1);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
						else
						{
							menew.setTotalpointswon_1(menew.getTotalpointswon_2()+1);
							int remaining = noofpointsplayed-8;
							if(remaining>0)
							{
								menew.setTotalpointswon_1(menew.getTotalpointswon_1()+remaining/2);
								menew.setTotalpointswon_2(menew.getTotalpointswon_2()+remaining/2);
							}
						}
					}

				}
			}
			// System.out.println("------------------------");


			Session session1 = HibernateUtil.getSessionFactory().openSession();
			session1.beginTransaction();
			session1.save(menew);
			session1.getTransaction().commit();
			session1.close();
		}
		actualGameNumber+=evenheads.size()+oddheads.size();
	}
	private static  boolean checkFile(String matchId, String string) {
		// TODO Auto-generated method stub
		//Check main page
		String url = "D:\\WTA\\Matches\\"+matchId+"\\"+string+".html";
		//System.out.println(url);
		File file = new File(url);
		if (file.exists()) 
		{
			return true;
		}
		else{
			//System.out.println("File Not Exist");
			return false;
		}
	}
	private static  boolean checkfolder(String matchId) {
		// TODO Auto-generated method stub
		String url = "D:\\WTA\\Matches\\"+matchId;
		File file = new File(url);
		if (!file.exists()) 
		{
			//System.out.println(file.getAbsolutePath());
			return false;
		}
		else{
			return true;
		}
	}
	public static PlayerRankHolder matchSummaryProcessor(WTAPlayerMatchIDMap  p) throws ArrayIndexOutOfBoundsException
	{
		try{

			PlayerRankHolder prh = new PlayerRankHolder();
			if(checkfolder(p.getMatchId()))
			{
				if(checkFile(p.getMatchId(),"matchsummary"))
				{
					File input1 = new File("D:\\WTA\\Matches\\"+p.getMatchId()+"\\"+"matchsummary"+".html");
					Document doc = Jsoup.parse(input1, "UTF-8", "http://example.com/");
					//	System.out.println(p.getMatchId());
					try{
						Element partOfNames = doc.getElementById("flashscore_column");
						//System.out.println(partOfNames.html());
						if(partOfNames!=null)
						{
							Elements table1 = partOfNames.select("td[class=tname-home face-enable]");
							if(table1!=null&&table1.size()>0)
							{
								String player1 = table1.get(0).text();
								prh.player1 = player1.substring(1,player1.length()-1);
							}
						}
						Elements table2 = partOfNames.select("td[class=tname-away face-enable]");
						if(table2!=null&&table2.size()>0)
						{
							String player2 = table2.get(0).text();
							prh.player2 = player2.substring(0, player2.length()-2);
							//						System.out.println(player1+" - "+player2);
							Element rankcontent = doc.getElementById("flashscore");
							Elements e1 = rankcontent.select("td[class=tface-home]");
							if(e1!=null)
							{			
								try
								{
									Element ele = e1.get(0);
									Elements eleranks=ele.select("span[class=participant-detail-rank]");
									if(eleranks!=null && eleranks.get(0) !=null)
									{
										String ranktext = eleranks.get(0).text();
										if(ranktext!=null && ranktext.length()>5)
										{

											String rankstring = ranktext.substring(5,ranktext.length()-1);
											if(rankstring!=null)
												prh.rank1 =Integer.parseInt(rankstring);
										}
									}
								}
								catch(Exception e)
								{

								}
							}
							Elements e2 = rankcontent.select("td[class=tface-away]");
							if(e2!=null)
							{
								try{
									Element ele = e2.get(0);
									Elements eleranks=ele.select("span[class=participant-detail-rank]");
									if(eleranks!=null && eleranks.get(0) !=null)
									{
										String ranktext2 = eleranks.get(0).text();
										if(ranktext2!=null && ranktext2.length()>5)
										{
											String rankstring2 = ranktext2.substring(5,ranktext2.length()-1);
											if(rankstring2!=null)
												prh.rank2 =Integer.parseInt(rankstring2);
										}
									}
								}catch(Exception e){}
							}
						}
						// For getting odds from summary page
						//System.out.println(" - Odd selection ");

						try
						{
							Elements odds = doc.getElementById("default-odds").select("tr[class=odd]").select("span[class=odds-wrap]");
							//System.out.println("sandeep - "+odds.size());
							if(odds.size()>1)
							{
								String odd1text = odds.get(0).text();
								//System.out.println(odds.get(0).text());
								String odd2text = odds.get(1).text();
								//System.out.println(odds.get(1).text());
								prh.odd1 = odd1text;
								prh.odd2 = odd2text;
							}
							else
							{
								Elements odds1 = doc.select("span[class=odds-wrap up]");
								//System.out.println(odds1.size());
								if(odds1 == null || odds1.size()<1)
								{
									prh.odd1 = "0";
								}
								else
								{
									prh.odd1= odds1.get(0).text();
								}
								//System.out.println(prh.odd1 + " - prh.odds1");
								Elements odds2 = doc.select("span[class=odds-wrap down]");
								if(odds2 == null || odds2.size()<1)
								{
									prh.odd2 = "0";
								}
								else{
									prh.odd2= odds2.get(0).text();
								}
							}
						}
						catch(Exception e)
						{

						}
						//System.out.println(prh.odd2 + " - prh.odds2");
						//selecting who won the game
						Elements selWon = doc.select("td[class=fl summary-horizontal]");


						//System.out.println(selWon + "selWON");
						if(selWon!=null && selWon.size()>1)
						{
							//	System.out.println("sandeep");
							//	System.out.println("Count of number of elements with this tag - "+selWon.size());
							Elements strong1 = selWon.get(0).select("strong");
							int strong1Size=0;int strong2Size=0;
							//System.out.println(strong1.size());
							strong1Size=strong1.size();
							Elements strong2 = selWon.get(1).select("strong");
							//System.out.println(strong2.size());
							strong2Size=strong2.size();

							if(strong1Size>strong2Size)
							{
								prh.won=1;
							}
							else
							{
								prh.won=2;
							}
						}
						else
						{
							prh.won=1;
						}
						//getting number of sets
						Elements setScoreElements = doc.select("td[class=score]").select("strong");
						//System.out.println(setScoreElements.size());
						int noofSetsP1Won = Integer.parseInt(setScoreElements.get(0).text().trim());
						int noofSetsP2Won = Integer.parseInt(setScoreElements.get(1).text().trim());
						int noofTotalSets =0;
						int noofTotalSetsPlayed = noofSetsP1Won+noofSetsP2Won;
						//
						prh.countOfSets=noofTotalSetsPlayed;
						if((noofSetsP1Won+noofSetsP2Won)==2)
						{
							noofTotalSets =3;
						}
						else
						{
							if((noofTotalSetsPlayed)==3 && (noofSetsP1Won==1 || noofSetsP2Won ==1 ))
							{
								noofTotalSets =3;
							}
							else
							{
								noofTotalSets =5;
							}
						}
						//System.out.println("noofTotalSets"+noofTotalSets);
						prh.countOfSetsInGame= noofTotalSets;
						//Getting set scores - 
						int setscr[] = new int[noofTotalSetsPlayed];
						Elements ep1 = doc.getElementById("parts").select("tr[class=odd]").select("td[class=score part],td[class=score part last]");
						Elements ep2 = doc.getElementById("parts").select("tr[class=even]").select("td[class=score part],td[class=score part last]");
						//System.out.println(ep1.size() + " - "+ep2.size());
						for(int i=0;i<noofTotalSetsPlayed;i++)
						{
							String actualSetScore=null;	
							String setP1Score = ep1.get(i).text();						
							String setP2Score = ep2.get(i).text();
							//	System.out.println(setP1Score + " - "+ setP2Score);
							//if P1 score has reached deuce then the number would be double digit
							int p1score = Integer.parseInt(setP1Score);
							int p2score = Integer.parseInt(setP2Score);
							if(p1score>10 || p2score>10)
							{
								//	System.out.println("Deuce has occured");
								if(p1score>p2score)
								{
									//	System.out.println("Game won by player one as his score would be like 7* ");
									actualSetScore="1"+setP1Score+setP2Score;								
								}
								else
								{
									actualSetScore="2"+setP1Score+setP2Score;
								}
							}
							if(p1score<10 || p2score<10)
							{
								if(p1score>p2score)
								{
									actualSetScore="1"+setP1Score+"0"+setP2Score+"0";								
								}
								else
								{
									actualSetScore="2"+setP1Score+"0"+setP2Score+"0";
								}
							}
							//	System.out.println(actualSetScore + " - "+i);
							setscr[i] = Integer.parseInt(actualSetScore);
						}
						prh.setscors=setscr;	


						//Getting Id's

						Elements ids = doc.getElementById("parts").select("td[class=fl summary-horizontal]").select("a");
						//	System.out.println(ids.size());

						//System.out.println(ids.text());

						String href = ids.get(0).attr("onclick");
						String url = href.substring(href.indexOf('('), href.indexOf(')'));
						String id = url.substring(url.length()-10,url.length()-2);
						//System.out.println(id);

						String href2 = ids.get(1).attr("onclick");
						String url2 = href2.substring(href2.indexOf('('), href2.indexOf(')'));
						String id2 = url2.substring(url2.length()-10,url2.length()-2);
						//	System.out.println(id2);

						//System.out.println(id+" - "+id2);

						prh.player1_id=id;
						prh.player2_id=id2;

						Element e = doc.getElementById("utime");
						prh.matchdate = (e!=null?e.text():"null");

						SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
						prh.gamedate=df.parse(prh.matchdate);
						System.out.println(prh.gamedate.toString());

						//System.out.println(prh.matchdate);

					}
					catch(IndexOutOfBoundsException e)
					{
						System.out.println("---------------------------------------------------------------------------------------");
						System.out.println("Exception");e.printStackTrace();
						System.out.println("---------------------------------------------------------------------------------------");
						//e.printStackTrace();
						System.out.println(p.getMatchId());

						String url = "D:\\WTA\\Matches\\"+p.getMatchId();
						File file = new File(url);
						System.out.println("file exists - one");
						if (file.exists()) 
						{
							System.out.println("file exists");
							File[] files = file.listFiles();
							if(files!=null) { //some JVMs return null for empty dirs
								for(File f: files) {
									f.delete();
								}
							}
						}
						System.out.println("file exists - two");
						file.delete();
						System.out.println("Enering session");
						Session s = HibernateUtil.getSessionFactory().openSession();
						Failedmatchids fm = (Failedmatchids) s.get(Failedmatchids.class, p.getMatchId());
						System.out.println(fm + " - FM");
						if(fm==null)
						{
							Failedmatchids fms = new Failedmatchids();
							fms.setMatchid(p.getMatchId());
							Session s1 = HibernateUtil.getSessionFactory().openSession();
							s1.save(fms);
						}


						//continue;
					}

				}
			}
			System.out.println("MATCH SUM END");

			return prh;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		System.out.println("Match summary end");
		return null;

	}
}
