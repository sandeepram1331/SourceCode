//RTF - Read to file - Read from file
package com.src.tennis.flashscore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mkyong.persistence.HibernateUtil;
import com.src.tennis.flashscore.entity.MatchEntry;
import com.src.tennis.flashscore.entity.PlayerMatchIDMap;

public class RTF_And_Put_Scores_of_Match_DB {


	public static void main(String[] args)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(PlayerMatchIDMap.class);
		ArrayList<PlayerMatchIDMap> fewmatches =  (ArrayList<PlayerMatchIDMap>) criteria.list();
		//System.out.println(fewmatches.size()+" - few matches size");
		int total=0;
		//System.out.println(fewmatches.size());
		for(PlayerMatchIDMap p : fewmatches)
		{
			if(!p.getMatchId().equals("0047WC8Q"))
			{
				continue;
			}
			try{
				//System.out.println(p.getMatchId()+"- First Line");
				if(p.getMatchId().contains("myg"))
				{
					continue;
				}
				PlayerRankHolder prh = matchSummaryProcessor(p);
				String player1=prh.player1;
				String player2 = prh.player2;
				int rank1=prh.rank1;
				int rank2 = prh.rank2;
				//odd fifteens_available
				if(checkFile(p.getMatchId(),"set1"))
				{
					//System.out.println("check file");
					setprocessor(p,"set1", player1,  player2,  rank1,  rank2,1);
				}
				if(checkFile(p.getMatchId(),"set2"))
				{
					System.out.println("Set2");
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
			catch(Exception e)
			{
				continue;
			}
		}
		System.out.println("---------------------------------------------------------------------------------------");
	}
	private static void setprocessor(PlayerMatchIDMap p, String set, String player1, String player2, int rank1, int rank2, int tab ) throws IOException
	{
		System.out.println("set processor - "+set);
		System.out.println("D:\\ATP\\Matches\\"+p.getMatchId()+"\\"+set+".html");
		File input = new File("D:\\ATP\\Matches\\"+p.getMatchId()+"\\"+set+".html");
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
			MatchEntry me = new MatchEntry();
			me.setPlayer1(player1);
			me.setPlayer2(player2);
			me.setMatchid(p.getMatchId());
			me.setRank1(rank1);
			me.setRank2(rank2);
			Elements lostserve = oddheads.get(z).select("td[class=match-history-vertical fr lostserve]");
			if(lostserve.size()>0)
			{
				me.setBreakserve(1);
			}
			else
			{
				me.setBreakserve(0);
			}
			if(i==2)
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
			Session session1 = HibernateUtil.getSessionFactory().openSession();
			session1.beginTransaction();
			session1.save(me);
			session1.getTransaction().commit();
			//System.out.println("Commited");
		}
		//System.out.println("second");
		for(int z=0;z<evenheads.size();z++)
		{

			MatchEntry me = new MatchEntry();
			me.setPlayer1(player1);
			me.setPlayer2(player2);
			me.setMatchid(p.getMatchId());
			me.setRank1(rank1);
			me.setRank2(rank2);
			Elements lostserve = evenheads.get(z).select("td[class=match-history-vertical fl lostserve]");
			if(lostserve.size()>0)
			{
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
			Session session1 = HibernateUtil.getSessionFactory().openSession();
			session1.beginTransaction();
			session1.save(me);
			session1.getTransaction().commit();
			//System.out.println("Commited");
		
		}
	}
	private static  boolean checkFile(String matchId, String string) {
		// TODO Auto-generated method stub
		//Check main page
		String url = "D:\\ATP\\Matches\\"+matchId+"\\"+string+".html";
		System.out.println(url);
		File file = new File(url);
		if (file.exists()) 
		{
			return true;
		}
		else{
			System.out.println("File Not Exist");
			return false;
		}
	}
	private static  boolean checkfolder(String matchId) {
		// TODO Auto-generated method stub
		String url = "D:\\ATP\\Matches\\"+matchId;
		File file = new File(url);
		if (!file.exists()) 
		{
			System.out.println(file.getAbsolutePath());
			return false;
		}
		else{
			return true;
		}
	}

	public static PlayerRankHolder matchSummaryProcessor( PlayerMatchIDMap p) throws IOException
	{
		PlayerRankHolder prh = new PlayerRankHolder();
		if(checkfolder(p.getMatchId()))
		{
			if(checkFile(p.getMatchId(),"matchsummary"))
			{
				File input1 = new File("D:\\ATP\\Matches\\"+p.getMatchId()+"\\"+"matchsummary"+".html");
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
							try{Element ele = e1.get(0);
							Elements eleranks=ele.select("span[class=participant-detail-rank]");
							String ranktext = eleranks.get(0).text();
							String rankstring = ranktext.substring(5,ranktext.length()-1);
							if(rankstring!=null)
								prh.rank1 =Integer.parseInt(rankstring);}
							catch(Exception e){}
						}

						Elements e2 = rankcontent.select("td[class=tface-away]");
						if(e2!=null)
						{
							try{
								Element ele = e2.get(0);
								Elements eleranks=ele.select("span[class=participant-detail-rank]");
								String ranktext2 = eleranks.get(0).text();
								String rankstring2 = ranktext2.substring(5,ranktext2.length()-1);
								if(rankstring2!=null)
									prh.rank2 =Integer.parseInt(rankstring2);
							}catch(Exception e){}
						}
					}

				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Match Summary Processor Exception");
					//AllProfileURLforMatch apm = new AllProfileURLforMatch();
					//apm.createDeletedFile(p.getMatchId());
					matchSummaryProcessor(p);
				}
			}
		}
		System.out.println("MATCH SUM END");
		return prh;
	}
}