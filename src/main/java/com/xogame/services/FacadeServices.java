package com.xogame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This Class to collecting all object that access to services.
 * 
 * @author Anas Abu-Hussein
 * @since 10/9/2018
 * 
 **/

@Component
public class FacadeServices {

	@Autowired
	private GameService gameService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MessageService messageService;

	public GameService getGameService() {
		return gameService;
	}

	public PlayerService getPlayerService() {
		return playerService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

}
