package com.xogame.operations;

import com.xogame.model.IniteGame;
import com.xogame.strategy_xo_game.GameStrategy;

public interface GameSteps {

//	public void beforeGameStart();

	/**
	 * Init the game strategy when it is have nul value.
	 * 
	 * @param initeGame
	 **/
	public void gameStart(IniteGame initeGame);

	/**
	 * In progress method that follow update game (strategy and settings) and player
	 * transactions ...
	 * 
	 * @param dbGame   // game comes from database ...
	 * @param comeGame // game comes from requst user ...
	 * @return
	 */
	public IniteGame gameInProgress(IniteGame dbGame, IniteGame comeGame);

	/**
	 * When end game ////
	 **/
	public void gameEnd();

}
