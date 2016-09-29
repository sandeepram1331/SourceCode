package com.src.tennis.flashscore.entity;
public class WTAPlayer {


	private int rank;
	private String name;
	private String nationality;
	private int rankColumnPoints ;
	private int rankColumnTournaments;
	private String playerURL;	
	private String playerEndURL;
	private String playerId;
	private String gender;
	
	
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
