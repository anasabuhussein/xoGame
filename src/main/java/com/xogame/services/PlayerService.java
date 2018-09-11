package com.xogame.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.xogame.dao.GeneralOperations;
import com.xogame.dao.PlayerDao;
import com.xogame.model.Player;
import com.xogame.model.PlayerState;
import com.xogame.repositry.FacadeRepositry;

@Service
public class PlayerService implements CommandLineRunner, GeneralOperations<Player>, PlayerDao {

	@Autowired
	private FacadeRepositry facadeRepositry;

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
	}

	@Override
	public Player save(Player t) {
		return facadeRepositry.getPlayerRepositry().save(t);
	}

	@Override
	public Player update(UUID id, Player _player) {
		Player player = findById(id);

		if (_player.getEmail() != null)
			player.setEmail(player.getEmail());

		if (_player.getPic() != null)
			player.setPic(_player.getPic());

		if (_player.getPass() != null)
			player.setPass(player.getPass());

		return save(player);
	}

	@Override
	public void delete(Player t) {
		facadeRepositry.getPlayerRepositry().delete(t);
	}

	@Override
	public void delete(UUID id) {
		Player player = findById(id);
		facadeRepositry.getPlayerRepositry().delete(player);
	}

	@Override
	public Player findById(UUID id) {
		Optional<Player> player = facadeRepositry.getPlayerRepositry().findById(id);
		if (player.isPresent())
			return player.get();
		return null;
	}

	@Override
	public List<Player> findAll() {
		return facadeRepositry.getPlayerRepositry().findAll();
	}

	@Override
	public List<Player> getFrinds(UUID id) {
		if (findById(id).getFriends() != null)
			return findById(id).getFriends();

		return null;
	}

	@Override
	public PlayerState getPlayerState(UUID id) {
		if (findById(id).getPlayerState() != null)
			return findById(id).getPlayerState();

		return null;
	}

}
