package com.src.entities;

import java.util.Date;

public class Matchentry implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String matchid;
	private String setid;
	private int gamenumber;
	private String score;
	private Integer breakserve;
	private Integer actualgamenumber;
	private Integer serveNumber;
	private int rank1;
	private int rank2;
	private String gender;



	
	private int totalpointswon_1;
	
	public int getTotalpointswon_1() {
		return totalpointswon_1;
	}

	public void setTotalpointswon_1(int totalpointswon_1) {
		this.totalpointswon_1 = totalpointswon_1;
	}

	public int getTotalpointswon_2() {
		return totalpointswon_2;
	}

	public void setTotalpointswon_2(int totalpointswon_2) {
		this.totalpointswon_2 = totalpointswon_2;
	}

	private int totalpointswon_2;

	public Matchentry() {
	}

	public Matchentry(String matchid, String setid, int gamenumber) {
		this.matchid = matchid;
		this.setid = setid;
		this.gamenumber = gamenumber;
	}

	public Matchentry(String matchid, String setid, int gamenumber, String score, Integer breakserve,
			Integer actualgamenumber, Integer serveNumber) {
		this.matchid = matchid;
		this.setid = setid;
		this.gamenumber = gamenumber;
		this.score = score;
		this.breakserve = breakserve;
		this.actualgamenumber = actualgamenumber;
		this.serveNumber = serveNumber;
	}

	public String getMatchid() {
		return this.matchid;
	}

	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}

	public String getSetid() {
		return this.setid;
	}

	public void setSetid(String setid) {
		this.setid = setid;
	}

	public int getGamenumber() {
		return this.gamenumber;
	}

	public void setGamenumber(int gamenumber) {
		this.gamenumber = gamenumber;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Integer getBreakserve() {
		return this.breakserve;
	}

	public void setBreakserve(Integer breakserve) {
		this.breakserve = breakserve;
	}

	public Integer getActualgamenumber() {
		return this.actualgamenumber;
	}

	public void setActualgamenumber(Integer actualgamenumber) {
		this.actualgamenumber = actualgamenumber;
	}

	public Integer getServeNumber() {
		return this.serveNumber;
	}

	public void setServeNumber(Integer serveNumber) {
		this.serveNumber = serveNumber;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Matchentry))
			return false;
		Matchentry castOther = (Matchentry) other;

		return ((this.getMatchid() == castOther.getMatchid()) || (this.getMatchid() != null
				&& castOther.getMatchid() != null && this.getMatchid().equals(castOther.getMatchid())))
				&& ((this.getSetid() == castOther.getSetid()) || (this.getSetid() != null
				&& castOther.getSetid() != null && this.getSetid().equals(castOther.getSetid())))
				&& (this.getGamenumber() == castOther.getGamenumber())
				&& ((this.getScore() == castOther.getScore()) || (this.getScore() != null
				&& castOther.getScore() != null && this.getScore().equals(castOther.getScore())))
				&& ((this.getBreakserve() == castOther.getBreakserve()) || (this.getBreakserve() != null
				&& castOther.getBreakserve() != null && this.getBreakserve().equals(castOther.getBreakserve())))
				&& ((this.getActualgamenumber() == castOther.getActualgamenumber())
						|| (this.getActualgamenumber() != null && castOther.getActualgamenumber() != null
						&& this.getActualgamenumber().equals(castOther.getActualgamenumber())))
				&& ((this.getServeNumber() == castOther.getServeNumber())
						|| (this.getServeNumber() != null && castOther.getServeNumber() != null
						&& this.getServeNumber().equals(castOther.getServeNumber())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getMatchid() == null ? 0 : this.getMatchid().hashCode());
		result = 37 * result + (getSetid() == null ? 0 : this.getSetid().hashCode());
		result = 37 * result + this.getGamenumber();
		result = 37 * result + (getScore() == null ? 0 : this.getScore().hashCode());
		result = 37 * result + (getBreakserve() == null ? 0 : this.getBreakserve().hashCode());
		result = 37 * result + (getActualgamenumber() == null ? 0 : this.getActualgamenumber().hashCode());
		result = 37 * result + (getServeNumber() == null ? 0 : this.getServeNumber().hashCode());
		return result;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
