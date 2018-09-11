package com.xogame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.uuid.Generators;

/**
 * 
 * Persistent class for Players ...
 * 
 * @author Anas Abu-Hussein
 * 
 * @since 7/9/2018
 **/
@Document(collection = "users")
public class Player {

	@Id
	@JsonProperty("id")
	private UUID id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("email")
	private String email;

	@JsonProperty("pass")
	private String pass;

	@JsonProperty("pic")
	private String pic;

	@JsonProperty("numberOfWin")
	private int numbersOfWin;

	@JsonProperty("numberOfLose")
	private int numbersOfLose;

	@JsonProperty("balance")
	private int balance;

	private boolean isOnline;

	private boolean isBusy;

	@JsonProperty("friends")
	private List<Player> friends = new ArrayList<>();

	private PlayerState playerState;

	public Player() {
		super();
	}

	public Player(String name, String email, String pass, String pic, int numbersOfWin, int numbersOfLose, int balance,
			List<Player> friends, PlayerState playerState) {
		super();
		this.id = Generators.timeBasedGenerator().generate();
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.pic = pic;
		this.numbersOfWin = numbersOfWin;
		this.numbersOfLose = numbersOfLose;
		this.balance = balance;
		this.friends = friends;
		this.playerState = playerState;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getNumbersOfWin() {
		return numbersOfWin;
	}

	public void setNumbersOfWin(int numbersOfWin) {
		this.numbersOfWin = numbersOfWin;
	}

	public int getNumbersOfLose() {
		return numbersOfLose;
	}

	public void setNumbersOfLose(int numbersOfLose) {
		this.numbersOfLose = numbersOfLose;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public List<Player> getFriends() {
		return friends;
	}

	public void setFriends(List<Player> friends) {
		this.friends = friends;
	}

}
