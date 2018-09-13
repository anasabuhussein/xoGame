package com.xogame.operations.game;

import com.xogame.model.IniteGame;

/**
 * This class represent the stage of game and it is operations such as game
 * start and within the game (in progress) and end the game.
 * 
 * @author Anas Abu-Hussein
 * @since 7/9/2018
 */
public interface GameSteps {

	/**
	 * Init the game strategy when it is have null value.
	 * 
	 * @param initeGame
	 **/
	public void gameStart(IniteGame initeGame);

	/**
	 * In progress method that follow update game (strategy and settings) and player
	 * transactions ...
	 * 
	 * @param dbGame   game comes from database ...
	 * @param comeGame game comes from requst user ...
	 * @return
	 */
	public IniteGame gameInProgress(IniteGame dbGame, IniteGame comeGame);

	/**
	 * when end the game set new state of game and new results of players .
	 * 
	 * @param restartGame this variable from request param when one player needs to
	 *                    restart the game.
	 * @param initGame TODO
	 */
	public void gameEnd(String restartGame, IniteGame initGame);

}
