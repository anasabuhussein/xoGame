package com.xogame.operations.game;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xogame.model.GameSetting.GAME_STATE;
import com.xogame.model.IniteGame;
import com.xogame.model.Player;
import com.xogame.model.PlayerState;
import com.xogame.model.PlayerState.PLAYER_RESULT_STATE;
import com.xogame.model.PlayerState.PLAYER_STATE;
import com.xogame.operations.game_setting.GameSettingStrategyImp;
import com.xogame.services.GameService;
import com.xogame.strategy_xo_game.GameStrategy;

/**
 * This class represent the operations on the game such as <br>
 * change state of game {@link GAME_STATE}, <br>
 * and change {@link PLAYER_STATE}, <br>
 * and set the chars of players and set {@link PLAYER_RESULT_STATE} when one
 * player is win or in balance
 * 
 * @author Anas Abu-Hussein
 * @since 11/9/2018
 *
 **/
@Component
public class GameOperations implements GameSteps {

	private static final Logger LOGGER = Logger.getLogger(GameOperations.class);

	@Autowired // to set new operations for each players and choose random chars
	private PlayerOperations playerOperations;

	@Autowired
	private GameService gameService;

	// to shortcut methode of initGame class. for coming initGame from DB.
	private GameSettingStrategyImp db_progress;

	// to shortcut methode of initGame class. for coming initGame from request.
	private GameSettingStrategyImp request_progress;

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

		if (!dbGame.getGameSetting().getGameState().equals(GAME_STATE.IN_PROGRESS.name())
				&& !dbGame.getGameSetting().getGameState().equals(GAME_STATE.END.name())) {
			initeGame = initProgressGame_PlayersAndChangeGameState(dbGame, comeGame);
		}

		if (dbGame.getGameSetting().getGameState().equals(GAME_STATE.IN_PROGRESS.name())) {

			// start playeng and change the patch 0...
			initeGame = inProgressGameStartPlay(dbGame, comeGame);
		}

		return initeGame;
	}

	@Override
	public void gameEnd(String restartGame, IniteGame initGame) {

		db_progress = new GameSettingStrategyImp(initGame);

		if ((restartGame != null && restartGame == "restart")
				|| initGame.getGameSetting().getGameState().equals(GAME_STATE.END.name())) {

			// set array to defaults array ...
			db_progress.getGameStrategy().defaultArray();

			// set in progress to all
			playerOperations.setPlayerStateResultForAll(db_progress.getPlayers(),
					PLAYER_RESULT_STATE.IN_PROGRESS.name());

			// set game state to in prgress ...
			db_progress.getGameSetting().setGameState(db_progress.getGameState());

			gameService.save(db_progress.getIniteGame());
		}

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

			db_progress = new GameSettingStrategyImp(dbGame);
			request_progress = new GameSettingStrategyImp(comeGame);

			if (db_progress.getPlayerSize() < 2) {

				if (playerOperations.checkPlayersExist(request_progress.getPlayers())) {
					// set new players ... if size in data base < 2
					// change sate of game from wating to start ...
					db_progress.setPlayers(request_progress.getPlayers());

					// set player state first time
					db_progress.setPlayerStateFirstTime(new PlayerState());
				}

			}

			// change state of game from waitting to start ...
			if (db_progress.getPlayerSize() >= 2)
				changeGameStateFromStartToProgress();

			if (db_progress.getPlayerSize() >= 2) {

				// detect Players and set players to player operation to detect chars
				Player player0 = db_progress.getPlayers().get(0);
				Player player1 = db_progress.getPlayers().get(1);

				// init players by set chars
				intiPlayersChar(player0, player1);

				// change the players who must start
				playerOperations.reversPlayers(db_progress.getPlayers());

				LOGGER.info("Player0 char is : " + player0.getPlayerState().getPlayerChars());
				LOGGER.info("Player1 char is : " + player1.getPlayerState().getPlayerChars());

				// change the state of player 0 from not ready to ready...
				playerOperations.changePlayerStates(player0);

				// change the state of player 1 to watting by set playing .
				playerOperations.changePlayerStateToWaitting(player1, PLAYER_STATE.PLAYING.name());

				// change state of game to progress ...
				changeGameStateFromStartToProgress();
			}

			return gameService.save(db_progress.getIniteGame());

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

			db_progress = new GameSettingStrategyImp(dbGame);
			request_progress = new GameSettingStrategyImp(comeGame);

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
			// set game state ..
			setFinalResult(db_progress.getPlayers());

			// check the array has d => defaults chars.
			checkPathAndsetFinalResult(db_progress.getPlayers(), db_progress);

			return gameService.save(db_progress.getIniteGame());

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
	 * @param players
	 */
	public void setFinalResult(List<Player> players) {

		if (playerOperations.checkPlayersIsWinners(players)) {

			// set the un winner player to loser result state ...
			playerOperations.detectWinnerPlayersToSetLoserPlayers(players);

			// change game state ...
			db_progress.setGameState(db_progress.getGameState());

		}
	}

	/**
	 * 
	 * If array not contains d char and the player final state is IN_PROGRESS // so
	 * set the result final state of player to balance...
	 * 
	 * @param players
	 * @param gameSetting
	 * @see Player
	 */
	public void checkPathAndsetFinalResult(List<Player> players, GameSettingStrategyImp gameSetting) {

		if (playerOperations.checkPlayersIsWinners(players) == false) {

			if (!db_progress.getGameStrategy().checkHasDefault_d()) {

				playerOperations.setPlayerStateResultForAll(players, PLAYER_RESULT_STATE.BALANCE.name());

				// update player result.
				playerOperations.updateStatePlayerNumberForAll(players);

				// change game state ...
				db_progress.setGameState(db_progress.getGameState());

			} else if (db_progress.getGameStrategy().checkHasDefault_d()) {
				playerOperations.setPlayerStateResultForAll(players, PLAYER_RESULT_STATE.IN_PROGRESS.name());
			}

		}

	}

}
