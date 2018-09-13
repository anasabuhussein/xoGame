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
import com.xogame.operations.game.GameOperations;
import com.xogame.services.FacadeServices;

/**
 * 
 * we have two type of game -> 1. already exist game in database and wait
 * players. 2. the player request friends to play with theme.
 **/

@RestController
public class GamePath {

	private static final Logger LOGGER = Logger.getLogger(GamePath.class);

	@Autowired
	private FacadeServices facadeServices;

	@Autowired
	private GameOperations gameOperations;

	@GetMapping(value = ("/players"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findAllPlayers() {
		return new ResponseEntity<>(facadeServices.getPlayerService().findAll(), HttpStatus.OK);
	}

	@GetMapping(value = ("/games"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findAllGames() {
		return new ResponseEntity<>(facadeServices.getGameService().findAll(), HttpStatus.OK);
	}

	@GetMapping(value = ("/games/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findAGameById(@PathVariable(value = "id") UUID id) {
		return new ResponseEntity<>(facadeServices.getGameService().findById(id), HttpStatus.OK);
	}

	@GetMapping(value = ("/games/waiting"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findWaitingGameById() {
		return new ResponseEntity<>(facadeServices.getGameService().waitStateGame(), HttpStatus.OK);
	}

	@PostMapping(value = ("/games"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> addGames(@RequestBody IniteGame game) {
		return new ResponseEntity<>(facadeServices.getGameService().save(game), HttpStatus.OK);
	}

	@PutMapping(value = ("/games/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateGames(@PathVariable(value = "id") UUID id, @RequestBody IniteGame initeGame) {

		return new ResponseEntity<>(facadeServices.getGameService().update(id, initeGame), HttpStatus.OK);
	}

	@SuppressWarnings("null")
	@PutMapping(value = ("/games/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> restartGame(@PathVariable(value = "id") UUID id, @RequestBody IniteGame initeGame,
			@RequestParam(value = "restart", required = true) String restart) {

		if (restart != null || !restart.equals(null))
			gameOperations.gameEnd(restart, initeGame);

		return new ResponseEntity<>(facadeServices.getGameService().update(id, initeGame), HttpStatus.OK);
	}

}
