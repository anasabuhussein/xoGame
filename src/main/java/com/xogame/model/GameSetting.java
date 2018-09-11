package com.xogame.model;

import java.util.ArrayList;
import java.util.List;

public class GameSetting {

	private String gameName;
	private String gamePass;

	private String gameState = GAME_STATE.WATTING.name();
	private List<Player> players = new ArrayList<>();

	public GameSetting() {
		super();
	}

	public GameSetting(String gameName, String gamePass, String gameState, List<Player> players) {
		super();
		this.gameName = gameName;
		this.gamePass = gamePass;
		this.gameState = gameState;
		this.players = players;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGamePass() {
		return gamePass;
	}

	public void setGamePass(String gamePass) {
		this.gamePass = gamePass;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		if (gameState.equals(GAME_STATE.WATTING.name()))
			this.gameState = GAME_STATE.START.name();

		if (gameState.equals(GAME_STATE.START.name()))
			this.gameState = GAME_STATE.IN_PROGRESS.name();

		if (gameState.equals(GAME_STATE.IN_PROGRESS.name()))
			this.gameState = GAME_STATE.END.name();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public enum GAME_STATE {
		WATTING, START, IN_PROGRESS, END;
	}
}
