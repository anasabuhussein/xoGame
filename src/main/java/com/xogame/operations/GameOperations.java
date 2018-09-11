package com.xogame.operations;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xogame.model.GameSetting.GAME_STATE;
import com.xogame.model.IniteGame;
import com.xogame.model.Player;
import com.xogame.model.PlayerState;
import com.xogame.model.PlayerState.PLAYER_RESULT_STATE;
import com.xogame.model.PlayerState.PLAYER_STATE;
import com.xogame.repositry.FacadeRepositry;
import com.xogame.strategy_xo_game.GameStrategy;

/**
 * @author anasa
 *
 */
/**
 * @author anasa
 *
 */
@Component
public class GameOperations implements GameSteps {

	private static final Logger LOGGER = Logger.getLogger(GameOperations.class);

	@Autowired
	private FacadeRepositry facadeRepositry;

	@Autowired
	private PlayerOperations playerOperations;

	private GameInProgressOperations db_progress;
	private GameInProgressOperations request_progress;

	public GameOperations() {
		super();
	}

	@Override
	public void gameStart(IniteGame initeGame) {
		// init strategy first time.
		LOGGER.info(" In start method ... ");
		try {
			if (initeGame.getGameStrategy() == null) {

				initeGame.setGameStrategy(new GameStrategy());
				initeGame.getGameStrategy().setPlayerState(new PlayerState());
				Iterator<?> i = initeGame.getGameSetting().getPlayers().iterator();
				while (i.hasNext()) {
					Player object = (Player) i.next();
					object.setPlayerState(new PlayerState());
				}

				initeGame.getGameStrategy().defaultArray();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public IniteGame gameInProgress(IniteGame dbGame, IniteGame comeGame) {

		LOGGER.info("In gameInprogress method ...");

		// init game and players and change players and game state
		IniteGame initeGame = null;

		if (!dbGame.getGameSetting().getGameState().equals(GAME_STATE.IN_PROGRESS.name())) {
			initeGame = this.initProgressGame_PlayersAndChangeGameState(dbGame, comeGame);
		}

		if (dbGame.getGameSetting().getGameState().equals(GAME_STATE.IN_PROGRESS.name())) {

			// start playeng and change the patch 0...
			initeGame = inProgressGameStartPlay(dbGame, comeGame);
		}

		return initeGame;
	}

	@Override
	public void gameEnd() {

	}

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
	 * @param id
	 * @return
	 */
	public Player findPlayer(UUID id) {

		// find player by set id from data base
		Optional<Player> player = facadeRepositry.getPlayerRepositry().findById(id);
		// if player relate with data base and has uniqe id.
		if (player.isPresent())
			return player.get();
		return null;
	}

	// change game state to progress ...
	public void changeGameStateFromStartToProgress() {
		if (!db_progress.getGameState().equals(GAME_STATE.IN_PROGRESS.name()))
			db_progress.setGameState(db_progress.getGameState());
	}

	// change game state from progress to end
	public void changeGameStateFromProgressToEnd() {
		if (db_progress.getGameState().equals(GAME_STATE.IN_PROGRESS.name()))
			db_progress.setGameState(db_progress.getGameState());
	}

	public IniteGame initProgressGame_PlayersAndChangeGameState(IniteGame dbGame, IniteGame comeGame) {

		try {

			db_progress = new GameInProgressOperations(dbGame);
			request_progress = new GameInProgressOperations(comeGame);

			if (db_progress.getPlayerSize() < 2) {

				if (checkPlayersExist(request_progress.getPlayers())) {
					// set new players ... if size in data base < 2
					// change sate of game from wating to start ...
					db_progress.setPlayers(request_progress.getPlayers());

					// set player state first time
					db_progress.setPlayerStateFirstTime(new PlayerState());
				}

			}

			// change state of game from waitting to start ...
			if (db_progress.getPlayerSize() >= 2)
				db_progress.setGameState(db_progress.getGameState());

			if (db_progress.getPlayerSize() >= 2) {

				// detect Players and set players to player operation to detect chars
				Player player0 = db_progress.getPlayers().get(0);
				Player player1 = db_progress.getPlayers().get(1);

				// init players by set chars
				intiPlayersChar(player0, player1);

				LOGGER.info("Player0 char is : " + player0.getPlayerState().getPlayerChars());
				LOGGER.info("Player1 char is : " + player1.getPlayerState().getPlayerChars());

				// change the state of player 0 from not ready to ready...
				playerOperations.changePlayerStates(player0);

				// change the state of player 1 to watting by set playing .
				playerOperations.changePlayerStateToWaitting(player1, PLAYER_STATE.PLAYING.name());

				// change state of game to progress ...
				changeGameStateFromStartToProgress();
			}

			return facadeRepositry.getGameRepositry().save(dbGame);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * This method set players chars.
	 * 
	 * @param player0
	 * @param player1
	 * @see Player
	 */
	private void intiPlayersChar(Player player0, Player player1) {
		// set random char -x- or -o- for the first player.
		playerOperations.chooceRandomChar(player0);

		// compare the players char for every player and set differnt chars.
		playerOperations.comparePlayersChars(player0, player1);
	}

	/**
	 * This method change players state to prepare who will play in the next step,
	 * and replace new chars requested of pathc in database patch.
	 * 
	 * @param dbGame   This param {@link IniteGame} selected from database
	 * @param comeGame This param {@link IniteGame} selected from request.
	 * @return {@link IniteGame}
	 * @see IniteGame
	 **/
	public IniteGame inProgressGameStartPlay(IniteGame dbGame, IniteGame comeGame) {

		try {

			db_progress = new GameInProgressOperations(dbGame);
			request_progress = new GameInProgressOperations(comeGame);

			// player 0 playing
			Player player0 = db_progress.getPlayers().get(0);

			// change to playing
			playerOperations.changePlayerStates(player0);

			if (player0.getPlayerState().getPlayerState().equals(PLAYER_STATE.PLAYING.name())) {
				playerRoleInGameProgress(player0);

				LOGGER.info("player0 is play");
				LOGGER.info("player1 is wait");
			}

			// player 0 playing
			Player player1 = db_progress.getPlayers().get(1);

			// change to playing
			playerOperations.changePlayerStates(player1);

			if (player1.getPlayerState().getPlayerState().equals(PLAYER_STATE.PLAYING.name())) {
				playerRoleInGameProgress(player1);

				LOGGER.info("player1 is play");
				LOGGER.info("player0 is wait");
			}

			// set player result state loser To other player ...
			setFinalResult(player0, player1);

			// check the array has d => defaults chars.
			checkPathAndsetFinalResult(player0, player1);

			return facadeRepositry.getGameRepositry().save(dbGame);

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	/**
	 * This methods provide logical step for every player when player has
	 * {@link PLAYER_STATE} PLAYING role to do some functions.
	 * 
	 * @param player
	 */
	public void playerRoleInGameProgress(Player player) {
		// set patch ...
		db_progress.getGameStrategy().replaceChars(request_progress.getGameStrategy().getPatch());

		// set player state to set chars and player state and result state.
		// set final result of player state if winner or in progress
		db_progress.getGameStrategy().setPlayerState(player.getPlayerState());

		// make comapre with array and char of player to detect the winner
		db_progress.getGameStrategy().doCompare();

		// new player state ..
		playerOperations.changePlayerStates(player);
	}

	/**
	 * Check the final result of players and set another player state to loser
	 * state.
	 * 
	 * @param player0
	 * @param player1
	 */
	public void setFinalResult(Player player0, Player player1) {

		// if player 0 is winner set player1 is loser
		if (player0.getPlayerState().getPlayerResultState().equals(PLAYER_RESULT_STATE.WINNER.name()))
			player1.getPlayerState().setPlayerResultState(PLAYER_RESULT_STATE.LOSER.name());

		// if player 1 is winner set player0 is loser
		if (player1.getPlayerState().getPlayerResultState().equals(PLAYER_RESULT_STATE.WINNER.name()))
			player0.getPlayerState().setPlayerResultState(PLAYER_RESULT_STATE.LOSER.name());

	}

	/**
	 * 
	 * If array not contains d char and the player final state is IN_PROGRESS // so
	 * set the result final state of player to balance...
	 * 
	 * @param player0
	 * @param player1
	 * 
	 * @see Player
	 */
	public void checkPathAndsetFinalResult(Player player0, Player player1) {

		if (!player0.getPlayerState().getPlayerResultState().equals(PLAYER_RESULT_STATE.WINNER.name())
				|| !player1.getPlayerState().getPlayerResultState().equals(PLAYER_RESULT_STATE.WINNER.name())) {

			if (!db_progress.getGameStrategy().checkHasDefault_d()) {

				player0.getPlayerState().setPlayerResultState(PLAYER_RESULT_STATE.BALANCE.name());
				player1.getPlayerState().setPlayerResultState(PLAYER_RESULT_STATE.BALANCE.name());

			} else if (db_progress.getGameStrategy().checkHasDefault_d()) {
				player0.getPlayerState().setPlayerResultState(PLAYER_RESULT_STATE.IN_PROGRESS.name());
				player1.getPlayerState().setPlayerResultState(PLAYER_RESULT_STATE.IN_PROGRESS.name());
			}

		}

	}

}
