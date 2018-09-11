package com.xogame.operations;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.xogame.model.Player;
import com.xogame.model.PlayerState.PLAYER_RESULT_STATE;
import com.xogame.model.PlayerState.PLAYER_STATE;

/**
 * This class has responsibility every operations the player do it.
 * <p>
 * - changing player state; from {@link PLAYER_STATE}
 * <p>
 * - set player charactors.
 * <p>
 * 
 * @author Anas Abu-Hussein
 * @since 9/9/2018
 * @see Player
 * @See PlayerState
 * 
 **/
@Component
public class PlayerOperations {

	private Logger LOGGER = Logger.getLogger(PlayerOperations.class);

	public PlayerOperations() {
		super();
	}

	/**
	 * @param player0
	 * @param player1
	 **/
	public void comparePlayersChars(Player player0, Player player1) {

		LOGGER.info("comparePlayersChars method ...");

		if (player0.getPlayerState().getPlayerChars().equals("x"))
			player1.getPlayerState().setPlayerChars("o");

		if (player1.getPlayerState().getPlayerChars().equals("o"))
			player0.getPlayerState().setPlayerChars("x");
	}

	/**
	 * This method get first player random char when game is start.
	 * 
	 * @param player
	 * 
	 * @see Player
	 **/
	public void chooceRandomChar(Player player) {

		LOGGER.info("chooceRandomChar method ...");

		Random random = new Random();
		String chars = "xo";
		for (int i = 0; i < 50; i++) {
			player.getPlayerState().setPlayerChars("" + (chars.charAt(random.nextInt(chars.length()))));
		}
	}

	/**
	 * This method change player state between {@link PLAYER_STATE}
	 * <p>
	 * NOt_READY ====> READY .
	 * <p>
	 * READY ====> PLAYING .
	 * <p>
	 * PLAYING ====> WAITTING.
	 * <p>
	 * WAITTING ====> READY.
	 * 
	 * @param player must set player to get player state and change it.
	 *
	 * @see Player
	 **/
	public void changePlayerStates(Player player) {
		player.getPlayerState().setPlayerState(player.getPlayerState().getPlayerState());
	}

	/**
	 * This method change player state to Watting.
	 * <p>
	 * Playing ====> Waitting.
	 * 
	 * @param player must set player to get player state and change it.
	 * @param state  must set the {@link PLAYER_STATE} PLAYING to change state of
	 *               player to waitting state.
	 * @see Player
	 **/
	public void changePlayerStateToWaitting(Player player, String state) {
		player.getPlayerState().setPlayerState(state);
	}

	/**
	 * set player state result of player {@link PLAYER_RESULT_STATE}
	 * 
	 * @param player Set player to set player result state ...
	 * @param state  Set specific state to change it, that you want.
	 **/
	public void setPlayerStateResult(Player player, String state) {
		player.getPlayerState().setPlayerResultState(state);
	}

	/**
	 * get player state result of player {@link PLAYER_RESULT_STATE}
	 * 
	 * @param player Get player of final result state ...
	 **/
	public String getPlayerStateResult(Player player) {
		return player.getPlayerState().getPlayerResultState();
	}

}
