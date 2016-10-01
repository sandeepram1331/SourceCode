package com.src.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "atpplayer")
public class ATPPlayer {


	@Id
	@Column(name="rank")
	private int rank;
	@Column(name="name")
	private String name;
	@Column(name="nationality")
	private String nationality;
	@Column(name="rankColumnPoints")
	private int rankColumnPoints ;
	@Column(name="rankColumnTournaments")
	private int rankColumnTournaments;
	@Column(name="playerURL")
	private String playerURL;
	@Column(name="playerEndURL")
	private String playerEndURL;
	@Column(name="playerId")
	private String playerId;
	@Column(name="gender")
	private String gender;
	@Column(name="date_added")
	private Date dateAdded;

	
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date date) {
		this.dateAdded = date;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public int getRankColumnPoints() {
		return rankColumnPoints;
	}
	public void setRankColumnPoints(int rankColumnPoints) {
		this.rankColumnPoints = rankColumnPoints;
	}
	public int getRankColumnTournaments() {
		return rankColumnTournaments;
	}
	public void setRankColumnTournaments(int rankColumnTournaments) {
		this.rankColumnTournaments = rankColumnTournaments;
	}
	public String getPlayerURL() {
		return playerURL;
	}
	public void setPlayerURL(String playerURL) {
		this.playerURL = playerURL;
	}
	public String getPlayerEndURL() {
		return playerEndURL;
	}
	public void setPlayerEndURL(String playerEndURL) {
		this.playerEndURL = playerEndURL;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
