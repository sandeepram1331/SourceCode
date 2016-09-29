package com.src.tennis.flashscore.entity;

public class PlayerURL {

	private  String playerName;
	private int playerrank;
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public String toString() {
		return "PlayerURL [playerName=" + playerName + ", playerrank=" + playerrank + ", playerURL=" + playerURL + "]";
	}
	public String getPlayerURL() {
		return playerURL;
	}
	public void setPlayerURL(String playerURL) {
		this.playerURL = playerURL;
	}
	public int getPlayerrank() {
		return playerrank;
	}
	public void setPlayerrank(int playerrank) {
		this.playerrank = playerrank;
	}
	private String playerURL;
}
