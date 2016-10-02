package com.src.entities;

import java.util.Arrays;
import java.util.Date;

public class PlayerRankHolder {

	public String player1 = null;
	public String player2 = null;
	public int rank1=0;
	public int rank2 = 0;
	public String player1_id = null;
	public String player2_id = null;
	public String odd1 = null;
	public String odd2 = null;
	public int won=0;
	public Integer countOfSets;
	public Integer countOfSetsInGame;
	public int setscors[] = null;
	public String matchdate;
	public Date gamedate;
	public String tournamentname;
	public String tournamentcountry;
	public String courttype;
	public String draw;
	@Override
	public String toString() {
		return "PlayerRankHolder [player1=" + player1 + ", player2=" + player2 + ", rank1=" + rank1 + ", rank2=" + rank2
				+ ", player1_id=" + player1_id + ", player2_id=" + player2_id + ", odd1=" + odd1 + ", odd2=" + odd2
				+ ", won=" + won + ", countOfSets=" + countOfSets + ", countOfSetsInGame=" + countOfSetsInGame
				+ ", setscors=" + Arrays.toString(setscors) + "]";
	}
}
