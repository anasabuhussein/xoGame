package com.xogame.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xogame.model.Player;
import com.xogame.services.FacadeServices;
import com.xogame.services.PicPlayerStreamDaoImpService;

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

	@Autowired
	private PicPlayerStreamDaoImpService picPlayerStreamDaoImpService;

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

	@GetMapping(value = ("/players/{id}/image"), produces = { MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> getPlayerPic(@PathVariable(value = "id") UUID id) throws FileNotFoundException {

		Player player = facadeServices.getPlayerService().findById(id);
		return new ResponseEntity<>(picPlayerStreamDaoImpService.readFromFolder_playerImage(player), HttpStatus.OK);
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
	
	@PostMapping(value = ("/players/upload"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> setNewPlayer(@RequestParam(value="name") String name,
			@RequestParam(value="pass") String pass,
			@RequestParam(value="email") String email,
			@RequestParam(value ="file") MultipartFile multipartFile) {
		LOGGER.info("Post Players ...");
		
		Player player = new Player();
		player.setPass(pass);
		player.setName(name);
		player.setEmail(email);
		
		try {
			picPlayerStreamDaoImpService.writeToFolder(multipartFile, player);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
//		facadeServices.getPlayerService().save(player)
		return new ResponseEntity<>(player, HttpStatus.OK);
	}
	

	@PutMapping(value = ("/players/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> setNewPlayerWitPic(@RequestParam(value = "file") MultipartFile multipartFile,
			@PathVariable(value = "id") UUID id) {

		Player player = facadeServices.getPlayerService().findById(id);
		try {
			picPlayerStreamDaoImpService.writeToFolder(multipartFile, player);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

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
	@DeleteMapping(value = ("/players/{id}"), produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") UUID id) {

		LOGGER.info("Delete Players with id : " + id);
		facadeServices.getPlayerService().delete(id);
		return new ResponseEntity<>("The object was deleted ..", HttpStatus.OK);
	}

}