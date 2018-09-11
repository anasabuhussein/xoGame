package com.xogame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.uuid.Generators;
import com.xogame.model.GameSetting;
import com.xogame.strategy_xo_game.GameStrategy;

@Document(collection = "games")
public class IniteGame {

	@Id
	private UUID id;

	@JsonProperty("gameSetting")
	private GameSetting gameSetting;

	@JsonProperty("gameStrategy")
	private GameStrategy gameStrategy;

	public IniteGame() {
		super();
		this.id = Generators.timeBasedGenerator().generate();
	}

	public IniteGame(GameSetting gameSetting, GameStrategy glayerStrategy) {
		super();
		this.id = Generators.timeBasedGenerator().generate();
		this.gameSetting = gameSetting;
		this.gameStrategy = glayerStrategy;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public GameStrategy getGameStrategy() {
		return gameStrategy;
	}

	public void setGameStrategy(GameStrategy gameStrategy) {
		this.gameStrategy = gameStrategy;
	}

	public GameSetting getGameSetting() {
		return gameSetting;
	}

	public void setGameSetting(GameSetting gameSetting) {
		this.gameSetting = gameSetting;
	}

	@JsonProperty("links")
	@Transient
	public List<String> links() {
		List<String> links = new ArrayList<>();
		links.add("/game/" + getId());

		return links;
	}

}
