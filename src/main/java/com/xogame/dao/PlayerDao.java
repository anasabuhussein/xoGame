package com.xogame.dao;

import java.util.List;
import java.util.UUID;

import com.xogame.model.Player;
import com.xogame.model.PlayerState;

public interface PlayerDao extends GeneralOperations<Player> {
	
	public List<Player> getFrinds(UUID ID);
			
	public PlayerState getPlayerState(UUID ID);
		
}
