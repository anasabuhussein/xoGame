package com.xogame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.uuid.Generators;

@Document(collection = "message")
public class Messages {

	@Id
	private UUID id;

	@JsonProperty("from")
	private Player from;

	@JsonProperty("to")
	private List<Player> to = new ArrayList<>();

	@JsonProperty("message")
	private String message;
	
	public Messages() {
		super();
		this.id = Generators.timeBasedGenerator().generate();
	}

	public Messages(Player from, List<Player> to, String message) {
		super();
		this.id = Generators.timeBasedGenerator().generate();
		this.from = from;
		this.to = to;
		this.message = message;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Player getFrom() {
		return from;
	}

	public void setFrom(Player from) {
		this.from = from;
	}

	public List<Player> getTo() {
		return to;
	}

	public void setTo(List<Player> to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
