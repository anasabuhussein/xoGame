package com.xogame.strategy_xo_game;

import org.apache.log4j.Logger;

import com.xogame.model.PlayerState;
import com.xogame.model.PlayerState.PLAYER_RESULT_STATE;

/**
 * This Class responaple on detect the Player is {@link PLAYER_RESULT_STATE} by
 * compare chars with multidimensional as default 3x3.
 * 
 * @author Anas Abu-Hussein
 * 
 * @since 8/9/2018
 *
 */
public class GameStrategy implements Strategy {

	private static final Logger LOGGER = Logger.getLogger(GameStrategy.class);

	private PlayerState playerState;
	private String[][] patch;

	public GameStrategy() {
		super();
	}

	public GameStrategy(PlayerState playerState) {
		super();
		this.playerState = playerState;
	}

	@Override
	public void defaultArray() {
		patch = new String[3][3];

		for (int i = 0; i < patch.length; i++) {
			for (int j = 0; j < patch[i].length; j++) {
				patch[i][j] = "d";
			}
		}
	}

	/**
	 * check if the array have d ==> defualt charactor .
	 * 
	 **/
	@Override
	public boolean checkHasDefault_d() {
		boolean check = true;
		for (int i = 0; i < patch.length; i++) {
			for (int j = 0; j < patch[i].length; j++) {
				if (!patch[i][j].equals("d")) {
					check = false;
				} else {
					check = true;
					break;
				}
			}
			if (check)
				break;
		}
		return check;
	}

	/**
	 * This method to make compare with other character in the array and usr
	 * character..
	 **/
	@Override
	public void doCompare() {
		strategyGroup1(patch);
	}

	/**
	 * This method for replace user character to the default array.
	 **/
	@Override
	public void replaceChars(String[][] comes) {
		for (int i = 0; i < patch.length; i++) {
			for (int j = 0; j < patch[i].length; j++) {
				patch[i][j] = comes[i][j];
			}
		}
	}

	/**
	 * search in rows and columns to get the winners ...
	 * 
	 * @param strategyGroup1
	 * @return
	 */
	public String[][] strategyGroup1(String[][] strategyGroup1) {

		boolean checkInRow = true; // check if winner or not in single row...
		for (int i = 0; i < strategyGroup1.length; i++) {
			if (checkEachElementInRow(strategyGroup1[i])) {

				playerState.setPlayerResultState(PLAYER_RESULT_STATE.WINNER.name());
				break;

			} else {
				checkInRow = false;
				playerState.setPlayerResultState(PLAYER_RESULT_STATE.IN_PROGRESS.name());
			}
		}

		// just if not exis in row search in column for winner...
		if (checkInRow == false) {
			if (checkEachElementInColumn(strategyGroup1)) {
				playerState.setPlayerResultState(PLAYER_RESULT_STATE.WINNER.name());
			} else {
				playerState.setPlayerResultState(PLAYER_RESULT_STATE.IN_PROGRESS.name());
			}
		}
		return strategyGroup1;
	}

	/**
	 * Check for each element in row in array.
	 * 
	 * @param rowElement
	 * @return
	 */
	public boolean checkEachElementInRow(String[] rowElement) {
		boolean check = false;
		for (int i = 0; i < rowElement.length; i++) {
			// for loop for reads all reacordes in array ...
			for (int j = 0; j < rowElement.length; j++) {
				// check elements that catched in i loop.
				if (playerState.getPlayerChars().equals(rowElement[j]) && (rowElement[i].equals(rowElement[j]))) {
					check = true;
				} else {
					check = false;
					break;
				}
			}
			if (check == false)
				break;
		}

		return check;
	}

	/**
	 * Check for each element in column in array.
	 * 
	 * @param rowElement
	 * @return
	 */
	public boolean checkEachElementInColumn(String[][] columnElement) {
		boolean check = false;

		try {
			for (int i = 0; i < columnElement.length; i++) {
				for (int j = 0; j < columnElement[i].length; j++) {
					// columnElement[j][i]
					for (int j2 = 0; j2 < columnElement[i].length; j2++) {
						if (playerState.getPlayerChars().equals(columnElement[j][i])
								&& columnElement[j][i].equals(columnElement[j2][i])) {
							check = true;
						} else {
							check = false;
							break;
						}
					}
					if (check == false || check)
						break;
				}

				if (check)
					break;
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return check;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public String[][] getPatch() {
		return patch;
	}
}
