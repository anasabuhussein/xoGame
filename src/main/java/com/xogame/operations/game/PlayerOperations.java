package com.xogame.operations.game;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xogame.model.Player;
import com.xogame.model.PlayerState;
import com.xogame.model.PlayerState.PLAYER_RESULT_STATE;
import com.xogame.model.PlayerState.PLAYER_STATE;
import com.xogame.services.PlayerService;

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

	@Autowired
	private PlayerService playerService;

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
	 * @see PlayerState
	 **/
	public void changePlayerStates(Player player) {
		player.getPlayerState().setPlayerState(player.getPlayerState().getPlayerState());
	}

	/**
	 * This method change player state to {@link PlayerState} directly.
	 * <p>
	 * Playing ====> Waitting.
	 * 
	 * @param player must set player to get player state and change it.
	 * @param state  must set the {@link PLAYER_STATE} PLAYING to change state of
	 *               player to waitting state.
	 * @see Player
	 * @see PlayerState
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

	// {@link #getPlayerState(Player) getPlayerState}
	/**
	 * This method will update the player {@link PLAYER_RESULT_STATE} and add +1
	 * when player is winner or loser or is balance by useing {@link PlayerService}
	 * to use implements dao methods. <br>
	 * To get player result state use {@link #getPlayerStateResult(Player)
	 * getPlayerStateResult}
	 * 
	 * @param player the player with final result {@link PLAYER_RESULT_STATE}.
	 * 
	 * @return the player with changed records.
	 */
	public Player updatePlayerStateNumbers(Player player) {

//		// get player by id from database ...
//		Player player = playerService.findById(_player.getId());

		// get player final result state ...
		String playerState = this.getPlayerStateResult(player);

		if (playerState.equals(PLAYER_RESULT_STATE.WINNER.name()))
			playerService.setPlayerBalance(player);

		if (playerState.equals(PLAYER_RESULT_STATE.LOSER.name()))
			playerService.setPlayerLose(player);

		if (playerState.equals(PLAYER_RESULT_STATE.BALANCE.name()))
			playerService.setPlayerWin(player);

		return player;
	}

	/**
	 * This method for check if the players exist in data base or not by using
	 * {@link #findPlayer(UUID) findPlayer} with access to {@link PlayerService}.
	 * 
	 * @param players
	 * @return true if it is exist in database, false if it is not exist.
	 * @see PlayerService
	 **/
	public boolean checkPlayersExist(List<Player> players) {
		// check if player exist in database or not before play.
		boolean check = false;

		// loop on all players in database .
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);

			// check if player is null or not
			if (findPlayer(player.getId()) != null) {
				check = true;
			} else {
				check = false;
			}
		}
		return check;
	}

	/**
	 * This method help to find player by uuid. <br>
	 * This method use {@link PlayerService #findById(UUID) findById} method.
	 * 
	 * @param id uuid of the player.
	 * @return player with same id if not has same id will return null.
	 * @see PlayerService
	 */
	@SuppressWarnings("null")
	public Player findPlayer(UUID id) {

		// find player by set id from data base
		Player player = playerService.findById(id);
		// if player relate with data base and has uniqe id.
		if (player != null || !player.equals(null))
			return player;
		return null;
	}

	/**
	 * This method will check if any player winners
	 * 
	 * @param players
	 * @see PLAYER_RESULT_STATE
	 **/
	public boolean checkPlayersIsWinners(List<Player> players) {

		boolean check = false;

		for (Player player : players) {
			if (getPlayerStateResult(player).equals(PLAYER_RESULT_STATE.WINNER.name())) {
				check = (true);
				break;
			} else {
				check = (false);
			}
		}
		return check;
	}

	/**
	 * This method will check the player is winner or not.
	 * 
	 * @param player
	 * @return true if the player is winner false if else that.
	 */
	public boolean checkPlayerWinner(Player player) {
		return getPlayerStateResult(player).equals(PLAYER_RESULT_STATE.WINNER.name());
	}

	/**
	 * This method will set player result state for all players directly.
	 * 
	 * @param players
	 * @param state
	 * @see PLAYER_RESULT_STATE
	 */
	public void setPlayerStateResultForAll(List<Player> players, String state) {
		for (Player player : players) {
			setPlayerStateResult(player, state);
		}
	}

	/**
	 * This method will detect winner players and set another players loser state
	 * result of game.<br>
	 * set state number records of the players .. using
	 * {@link #updatePlayerStateNumbers(Player) updatePlayerStateNumbers} method.
	 * 
	 * 
	 * @param players
	 */
	public void detectWinnerPlayersToSetLoserPlayers(List<Player> players) {

		for (Player player : players) {
			if (checkPlayerWinner(player) == false) {
				// player is loser
				setPlayerStateResult(player, PLAYER_RESULT_STATE.LOSER.name());
				updatePlayerStateNumbers(player);
			} else if (checkPlayerWinner(player)) {
				// player is winner
				updatePlayerStateNumbers(player);
			} else {
				// when player in balanse ..
				updatePlayerStateNumbers(player);
			}
		}
	}

	/**
	 * Set records number for all players. by using {@link PlayerService} and
	 * methods {@link PlayerService #setPlayerWin(Player)},
	 * {@link PlayerService #setPlayerLose(Player)},
	 * {@link PlayerService #setPlayerBalance(Player)} <br>
	 * These methods are orgnized in one method that compare player state result
	 * {@link PLAYER_RESULT_STATE} by using
	 * {@link #updatePlayerStateNumbers(Player)}
	 * 
	 * @param players
	 * @see PlayerState
	 * @see PlayerService
	 * @see PLAYER_RESULT_STATE
	 */
	public void updateStatePlayerNumberForAll(List<Player> players) {
		for (Player player : players) {
			updatePlayerStateNumbers(player);
		}
	}

	/**
	 * Change the Player Who will start first in each game...
	 * 
	 * @param players List of players that will playes.
	 */
	public void reversPlayers(List<Player> players) {
//		Collections.reverse(players);

		int zero = 0; // prepare first index in list
		int last = players.size() - 1; // prepare last index in list.

		Player firstPlayer = players.get(zero); // get first Player all the time.
		Player lastPlayer = players.get(last); // get last players all the time.

		players.set(last, firstPlayer); // change index last to first player..
		players.set(zero, lastPlayer); // cange index first to last player ..
	}

}
