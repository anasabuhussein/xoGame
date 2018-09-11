package com.xogame.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.xogame.dao.GeneralOperations;
import com.xogame.model.Player;
import com.xogame.repositry.PlayerRepositry;

@Service
public class PlayerService implements CommandLineRunner, GeneralOperations<Player> {

	@Autowired
	private PlayerRepositry pr;

	@Override
	public void run(String... args) throws Exception {
		Player player0 = new Player("anas", "anas@me.com", "159954", "pic.png", 0, 0, 0, null, null);

		Player player1 = new Player("bassam", "anas@me.com", "159954", "pic.png", 0, 0, 0, null, null);

		Player player2 = new Player("hussein", "anas@me.com", "159954", "pic.png", 0, 0, 0, null, null);

		Player player3 = new Player("youness", "anas@me.com", "159954", "pic.png", 0, 0, 0, null, null);

		Player player4 = new Player("maha", "anas@me.com", "159954", "pic.png", 0, 0, 0, null, null);

//		this.save(player0);
//		this.save(player1);
//		this.save(player2);
//		this.save(player3);
//		this.save(player4);
	}

	public PlayerService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Player save(Player t) {

		return pr.save(t);
	}

	@Override
	public Player update(UUID id, Player t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Player t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Player findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> findAll() {
		return pr.findAll();
	}

}
