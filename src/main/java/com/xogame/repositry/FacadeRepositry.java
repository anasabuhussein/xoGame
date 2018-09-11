package com.xogame.repositry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class FacadeRepositry {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private GameRepositry gameRepositry;

	@Autowired
	private PlayerRepositry playerRepositry;

	@Autowired
	private MessagesRepositry messagesRepositry;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public GameRepositry getGameRepositry() {
		return gameRepositry;
	}

	public PlayerRepositry getPlayerRepositry() {
		return playerRepositry;
	}

	public MessagesRepositry getMessagesRepositry() {
		return messagesRepositry;
	}

}
