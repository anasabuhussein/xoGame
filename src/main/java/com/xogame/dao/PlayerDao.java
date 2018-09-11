package com.xogame.dao;

import java.util.List;

import com.xogame.model.Player;
import com.xogame.model.PlayerState;

public interface PlayerDao extends GeneralOperations<Player> {
	
	public List<Player> getFrinds();
	
	public boolean checkBusy();
	
	public boolean checkOnline();
		
	public PlayerState getPlayerState();
		
}
