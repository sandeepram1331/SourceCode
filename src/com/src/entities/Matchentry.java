package com.src.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "matchentry")
public class Matchentry implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name="matchid")
	private String matchid;
	@Id
	@Column(name="setid")
	private String setid;
	@Column(name="gamenumber")
	private int gamenumber;
	@Column(name="score")
	private String score;
	@Column(name="breakserve")
	private Integer breakserve;
	@Column(name="actualgamenumber")
	private Integer actualgamenumber;
	@Column(name="serveNumber")
	private Integer serveNumber;
	@Column(name="rank1")
	private int rank1;
	@Column(name="rank2")
	private int rank2;
	@Column(name="gender")
	private String gender;
	@Column(name="servername")
	private String servername;
	@Column(name="player1")
	private String player1;
	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
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

	public String getTournamentname() {
		return tournamentname;
	}

	public void setTournamentname(String tournamentname) {
		this.tournamentname = tournamentname;
	}

	public String getTournamentcountry() {
		return tournamentcountry;
	}

	public void setTournamentcountry(String tournamentcountry) {
		this.tournamentcountry = tournamentcountry;
	}

	public String getCourttype() {
		return courttype;
	}

	public void setCourttype(String courttype) {
		this.courttype = courttype;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getGamewonby() {
		return gamewonby;
	}

	public void setGamewonby(String gamewonby) {
		this.gamewonby = gamewonby;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="player2")
	private String player2;
	@Column(name="tournamentname")
	private String tournamentname;
	@Column(name="tournamentcountry")
	private String tournamentcountry;
	@Column(name="courttype")
	private String courttype;
	@Column(name="draw")
	private String draw;
	@Column(name="gamewonby")
	private String gamewonby;



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
