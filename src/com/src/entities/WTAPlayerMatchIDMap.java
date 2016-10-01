package com.src.entities;

import javax.persistence.Column;
import javax.persistence.Id;

public class WTAPlayerMatchIDMap {


	@Id
	@Column(name="matchId")
	private String matchId;
	@Column(name="playername")
	private String playername;
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}


}
