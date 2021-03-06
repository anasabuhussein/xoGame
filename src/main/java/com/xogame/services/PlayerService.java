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
		//
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

	@Override
	public int getBalanceOfPlayer(Player player) {
		return player.getBalance();
	}

	@Override
	public int getWinningOfPlayer(Player player) {
		return player.getNumbersOfWin();
	}

	@Override
	public int getLosesOfPlayer(Player player) {
		return player.getNumbersOfLose();
	}

	@Override
	public void setPlayerWin(Player player) {
		player.setBalance(getBalanceOfPlayer(player) + 1);
	}

	@Override
	public void setPlayerLose(Player player) {
		player.setNumbersOfLose(getLosesOfPlayer(player) + 1);
	}

	@Override
	public void setPlayerBalance(Player player) {
		player.setNumbersOfWin(getWinningOfPlayer(player) + 1);
	}

}
