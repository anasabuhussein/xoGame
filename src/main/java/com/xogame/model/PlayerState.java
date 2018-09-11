package com.xogame.model;

/**
 * 
 * This class for get the player character and get players role and the final
 * result of state.
 * 
 * @author Anas Abu-Hussein
 * 
 * @since 7/9/2018
 **/
public class PlayerState {

	private String playerChars;
	private String playerResultState = PLAYER_RESULT_STATE.IN_PROGRESS.name(); // final result of game !
	private String _playerState = PLAYER_STATE.NOT_READY.name(); // role in game !

	public PlayerState() {
		super();
	}

	public PlayerState(String playerChars, String playerResultState, String playerState) {
		super();
		this.playerChars = playerChars;
		this.playerResultState = playerResultState;
		this._playerState = playerState;
	}

	public String getPlayerChars() {
		return playerChars;
	}

	public void setPlayerChars(String playerChars) {
		this.playerChars = playerChars;
	}

	public String getPlayerResultState() {
		return playerResultState;
	}

	public void setPlayerResultState(String playerResultState) {
		this.playerResultState = playerResultState;
	}

	public String getPlayerState() {
		return _playerState;
	}

	public void setPlayerState(String playerState) {

		if (playerState.equals(PLAYER_STATE.NOT_READY.name()))
			this._playerState = PLAYER_STATE.READY.name();

		if (playerState.equals(PLAYER_STATE.READY.name()))
			this._playerState = PLAYER_STATE.PLAYING.name();

		if (playerState.equals(PLAYER_STATE.PLAYING.name()))
			this._playerState = PLAYER_STATE.WAITTING.name();

		if (playerState.equals(PLAYER_STATE.WAITTING.name()))
			this._playerState = PLAYER_STATE.READY.name();
	}

	public enum PLAYER_RESULT_STATE {
		WINNER, LOSER, BALANCE, IN_PROGRESS;
	}

	public enum PLAYER_STATE {
		READY, WAITTING, PLAYING, NOT_READY;
	}

}
