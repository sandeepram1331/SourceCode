package com.src.tennis.flashscore.entity;

public class MatchEntry {

	private String matchid;
	private String setid;
	private int gamenumber;
	private int servernumber;
	private String point1;
	private String point2;
	private String point3;
	private String point4;
	private String point5;
	private String point6;
	private String point7;
	private int breakserve;
	private String player1;
	private String player2;
	private int rank1;
	private int rank2;
	public String getMatchid() {
		return matchid;
	}
	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}
	public String getSetid() {
		return setid;
	}
	public void setSetid(String setid) {
		this.setid = setid;
	}
	public int getGamenumber() {
		return gamenumber;
	}
	public void setGamenumber(int gamenumber) {
		this.gamenumber = gamenumber;
	}
	public String getPoint1() {
		return point1;
	}
	public void setPoint1(String point1) {
		this.point1 = point1;
	}
	public String getPoint2() {
		return point2;
	}
	public void setPoint2(String point2) {
		this.point2 = point2;
	}
	public String getPoint3() {
		return point3;
	}
	public void setPoint3(String point3) {
		this.point3 = point3;
	}
	public String getPoint4() {
		return point4;
	}
	public void setPoint4(String point4) {
		this.point4 = point4;
	}
	public String getPoint5() {
		return point5;
	}
	public void setPoint5(String point5) {
		this.point5 = point5;
	}
	public String getPoint6() {
		return point6;
	}
	public void setPoint6(String point6) {
		this.point6 = point6;
	}
	public String getPoint7() {
		return point7;
	}
	public void setPoint7(String point7) {
		this.point7 = point7;
	}
	public int getBreakserve() {
		return breakserve;
	}
	public void setBreakserve(int breakserve) {
		this.breakserve = breakserve;
	}
	
	public int getRank1() {
		return rank1;
	}
	public void setRank1(int rank1) {
		this.rank1 = rank1;
	}
	public int getRank2() {
		return rank2;
	}
	public void setRank2(int rank2) {
		this.rank2 = rank2;
	}
	public int getServernumber() {
		return servernumber;
	}
	public void setServernumber(int servernumber) {
		this.servernumber = servernumber;
	}
	public String getPlayer1() {
		return player1;
	}
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}
	public String getPlayer2() {
		return player2;
	}
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	/*create table matchentry
(
matchid varchar(100),
	  setid varchar(100),
	  gamenumber int,
	  servernumber int,
	  point1 varchar(100),
	  point2 varchar(100),
	  point3 varchar(100),
	  point4 varchar(100),
	  point5 varchar(100),
	  point6 varchar(100),
	  point7 varchar(100),
	  breakserve int,
	  player1 varchar(100),
	  player2 varchar(100),
	  rank1 int,
	  rank2 int
    );
    
    
    ALTER TABLE matchentry ADD PRIMARY KEY(matchid,setid,gamenumber);*/
	
}
