package com.src.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WTAplayermatchids")
public class WTAPlayerMatchids {

	@Column(name="playername")
	private String playername;
	@Id
	@Column(name="matchid")
	private String matchId;
	
	
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