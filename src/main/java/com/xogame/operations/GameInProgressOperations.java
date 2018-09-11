package com.xogame.operations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.xogame.dao.GameSettingDao;
import com.xogame.model.GameSetting;
import com.xogame.model.IniteGame;
import com.xogame.model.Player;
import com.xogame.model.PlayerState;
import com.xogame.strategy_xo_game.GameStrategy;

/**
 * 
 * This class it is represent the operation must perform to acces to data. and
 * make blueprint.
 * 
 * @author Anas Abu-Hussein
 * 
 * @since 7/9/2018
 **/

public class GameInProgressOperations implements GameSettingDao {

	private IniteGame initeGame;

	public GameInProgressOperations() {
		super();
	}

	public GameInProgressOperations(IniteGame initeGame) {
		super();
		this.initeGame = initeGame;
	}

	@Override
	public void setGameName(String name) {
		if (name != null)
			initeGame.getGameSetting().setGameName(name);
	}

	@Override
	public void setGamePass(String pass) {
		if (pass != null)
			initeGame.getGameSetting().setGamePass(pass);
	}

	@Override
	public void setGameState(String state) {
		if (state != null)
			initeGame.getGameSetting().setGameState(state);
	}

	@Override
	public String getGameName() {
		return initeGame.getGameSetting().getGameName();
	}

	@Override
	public String getGamePass() {
		return initeGame.getGameSetting().getGamePass();
	}

	@Override
	public String getGameState() {
		return initeGame.getGameSetting().getGameState();
	}

	@Override
	public int getPlayerSize() {
		return initeGame.getGameSetting().getPlayers().size();
	}

	@Override
	public void setPlayers(List<Player> players) {

		Map<UUID, Player> mapPlayer = null;

		try {

			mapPlayer = new HashMap<>();

			if (players != null && getPlayerSize() < 2) {
				// add from database to map
				for (int i = 0; i < getPlayerSize(); i++) {
					mapPlayer.put(getPlayers().get(i).getId(), getPlayers().get(i));
				}

				// if have new user add to map
				for (int i = 0; i < players.size(); i++) {
					mapPlayer.put(players.get(i).getId(), players.get(i));
				}

				// remove list from initGame database ..
				getPlayers().clear();

				// move map to initgame list
				Iterator<UUID> key = mapPlayer.keySet().iterator();
				while (key.hasNext()) {
					UUID id = key.next();
					Player player = mapPlayer.get(id);
					getPlayers().add(player);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public List<Player> getPlayers() {
		return initeGame.getGameSetting().getPlayers();
	}

	@Override
	public void setPlayerStateFirstTime(PlayerState playerState) {

		try {

			if (playerState != null) {

				Iterator<Player> iterator = this.getPlayers().iterator();

				while (iterator.hasNext()) {
					Player player = iterator.next();
					if (getPlayerState(player) == null) {
						player.setPlayerState(new PlayerState());
					} else {
						player.setPlayerState(playerState);
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void setPlayerState(PlayerState playerState, Player player) {
		player.setPlayerState(playerState);
	}

	@Override
	public PlayerState getPlayerState(Player player) {
		try {

			if (player != null)
				return player.getPlayerState();

			throw new Exception();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public GameSetting getGameSetting() {
		return initeGame.getGameSetting();
	}

	@Override
	public void setGameSetting(GameSetting gameSetting) {
		if (gameSetting != null)
			initeGame.setGameSetting(gameSetting);
	}

	public GameStrategy getGameStrategy() {
		return initeGame.getGameStrategy();
	}

	public IniteGame getIniteGame() {
		return initeGame;
	}

	public void setIniteGame(IniteGame initeGame) {
		if (initeGame != null)
			this.initeGame = initeGame;
	}

}
