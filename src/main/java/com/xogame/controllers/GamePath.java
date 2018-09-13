package com.xogame.controllers;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xogame.model.IniteGame;
import com.xogame.model.GameSetting.GAME_STATE;
import com.xogame.operations.game.GameOperations;
import com.xogame.operations.game_setting.GameSettingStrategyImp;
import com.xogame.services.FacadeServices;

/**
 * RestController to get end points of game; and gets data of game.
 * 
 * @author Anas Abu-Hussein
 * @since 11/9/2018
 * @see IniteGame
 *
 **/

@RestController
public class GamePath {

	private static final Logger LOGGER = Logger.getLogger(GamePath.class);

	@Autowired
	private FacadeServices facadeServices;

	@Autowired
	private GameOperations gameOperations;

	@GetMapping(value = ("/games"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findAllGames() {
		LOGGER.info("Get Games");
		return new ResponseEntity<>(facadeServices.getGameService().findAll(), HttpStatus.OK);
	}

	@GetMapping(value = ("/games/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findAGameById(@PathVariable(value = "id") UUID id) {

		LOGGER.info("Get Games By ID : " + id);
		return new ResponseEntity<>(facadeServices.getGameService().findById(id), HttpStatus.OK);
	}

	@GetMapping(value = ("/games/waiting"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findWaitingGameById() {
		LOGGER.info("Get Game with Waiting state ");
		return new ResponseEntity<>(facadeServices.getGameService().waitStateGame(), HttpStatus.OK);
	}

	@PostMapping(value = ("/games"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> addGames(@RequestBody IniteGame game) {

		LOGGER.info("Post Method new game");

		// set game first time ...
		gameOperations.gameStart(game);

		return new ResponseEntity<>(facadeServices.getGameService().save(game), HttpStatus.OK);
	}

	@PutMapping(value = ("/games/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> restartGame(@PathVariable(value = "id") UUID id, @RequestBody IniteGame initeGame,
			@RequestParam(value = "restart", required = false) String restart) {

		LOGGER.info("Put Method wit exist game has id : " + id);

		IniteGame dbGame = facadeServices.getGameService().findById(id);
		GameSettingStrategyImp gameSettingStrategyImp = new GameSettingStrategyImp(dbGame);

		// start
		if (gameSettingStrategyImp.getGameSetting().getGameState().equals(GAME_STATE.END.name())) {

			LOGGER.info("Put Game with start state");

			gameOperations.gameStart(dbGame);
		}

		// change state from waiting to start
		if (restart == null && gameSettingStrategyImp.getGameState().equals(GAME_STATE.WATTING.name())) {

			LOGGER.info("Put Game with waiting state ");

			gameSettingStrategyImp.setGameState(gameSettingStrategyImp.getGameState());
		}

		// in progress
		if (restart == null && gameSettingStrategyImp.getGameState().equals(GAME_STATE.START.name())) {

			LOGGER.info("Put Game with start ");

			gameSettingStrategyImp.setIniteGame(gameOperations.gameInProgress(dbGame, initeGame));
		}

		// end
		if (restart != null && gameSettingStrategyImp.getGameState().equals(GAME_STATE.END.name())) {

			LOGGER.info("Put Game with end state and not null restart ... ");

			gameOperations.gameEnd(restart, dbGame);
		}

		return new ResponseEntity<>(facadeServices.getGameService().update(gameSettingStrategyImp.getIniteGame()),
				HttpStatus.OK);

	}

}
