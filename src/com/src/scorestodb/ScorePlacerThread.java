package com.src.scorestodb;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.src.entities.WTAPlayerMatchIDMap;
import com.src.hibernateutil.HibernateUtil;

public class ScorePlacerThread {

	public static void main(String a[])
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(WTAPlayerMatchIDMap.class);
		ArrayList<WTAPlayerMatchIDMap> fewmatches =  (ArrayList<WTAPlayerMatchIDMap>) criteria.list();

		Thread[] t = new Thread[10];

		for(int i=0;i<44;i++)
		{
				t[i] = new Thread(new WTAScoresPlacer(fewmatches.subList(i*1000, (i+1)*1000)));
				t[i].start();
		}

	}

	private static Runnable WTAScoresPlacer(List<WTAPlayerMatchIDMap> subList) {
		// TODO Auto-generated method stub
		return null;
	}

}
