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
import org.springframework.web.bind.annotation.RestController;

import com.xogame.model.Player;
import com.xogame.services.FacadeServices;

/**
 * RestController to get end points of Player; and gets data of Player.
 * 
 * @author Anas Abu-Hussein
 * @since 11/9/2018
 * @see Player
 *
 **/

@RestController
public class PlayerController {

	private static final Logger LOGGER = Logger.getLogger(PlayerController.class);

	@Autowired
	private FacadeServices facadeServices;

	/**
	 * Get methods : This method to find all players ....
	 * 
	 **/
	@GetMapping(value = ("/players"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findAllPlayers() {

		LOGGER.info("Get Players ...");
		return new ResponseEntity<>(facadeServices.getPlayerService().findAll(), HttpStatus.OK);
	}

	/**
	 * Get method : get Player by id
	 **/
	@GetMapping(value = ("/players/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> findAllPlayers(@PathVariable(value = "id") UUID id) {

		LOGGER.info("Get Players with id : " + id);
		return new ResponseEntity<>(facadeServices.getPlayerService().findById(id), HttpStatus.OK);
	}

	/**
	 * Post methods : This method to set new player ..
	 **/
	@PostMapping(value = ("/players"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> setNewPlayer(@RequestBody Player player) {
		LOGGER.info("Post Players ...");
		return new ResponseEntity<>(facadeServices.getPlayerService().save(player), HttpStatus.OK);
	}

	/**
	 * Put methods : This method to update player ..
	 **/
	@PutMapping(value = ("/players/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updatePlayer(@PathVariable(value = "id") UUID id, @RequestBody Player _player) {

		LOGGER.info("Put Players with id : " + id);
		return new ResponseEntity<>(facadeServices.getPlayerService().update(id, _player), HttpStatus.OK);
	}

	/**
	 * Delete methods : This method to delete player ..
	 **/
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") UUID id) {

		LOGGER.info("Delete Players with id : " + id);
		facadeServices.getPlayerService().delete(id);
		return new ResponseEntity<>("The object was deleted ..", HttpStatus.OK);
	}

}
