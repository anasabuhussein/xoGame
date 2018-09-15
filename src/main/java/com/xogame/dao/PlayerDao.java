package com.xogame.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.xogame.model.Player;
import com.xogame.model.PlayerState;

/**
 * This class for increase the operations that gets data from database for player.
 * 
 * @author Anas Abu-Hussein
 * @since 11/9/2018
 *
 **/

public interface PlayerDao extends GeneralOperations<Player> {

	public List<Player> getFrinds(UUID ID);

	public PlayerState getPlayerState(UUID ID);

	public int getBalanceOfPlayer(Player player);

	public int getWinningOfPlayer(Player player);

	public int getLosesOfPlayer(Player player);

	public void setPlayerWin(Player player);

	public void setPlayerLose(Player player);

	public void setPlayerBalance(Player player);

}
