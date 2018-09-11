package com.xogame.dao;

import java.util.List;

import com.xogame.model.GameSetting;
import com.xogame.model.Player;
import com.xogame.model.PlayerState;

public interface GameSettingDao {

	public void setGameName(String name);

	public void setGamePass(String pass);

	public void setGameState(String state);

	public String getGameName();

	public String getGamePass();

	public String getGameState();

	public int getPlayerSize();

	public void setPlayers(List<Player> players);

	public List<Player> getPlayers();
	
	public void setPlayerStateFirstTime(PlayerState playerState);

	public void setPlayerState(PlayerState playerState, Player player);

	public PlayerState getPlayerState(Player player);

	public GameSetting getGameSetting();

	public void setGameSetting(GameSetting GameSetting);

}
