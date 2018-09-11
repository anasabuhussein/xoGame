package com.xogame.strategy_xo_game;

import com.xogame.model.PlayerState;

public interface Strategy {

	public void doCompare();

	public void defaultArray();

	public void replaceChars(String[][] comes);

	public boolean checkHasDefault_d();

	public String[][] getPatch();

	public PlayerState getPlayerState();

	public void setPlayerState(PlayerState playerState);

}
