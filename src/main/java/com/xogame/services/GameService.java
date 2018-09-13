package com.xogame.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.xogame.dao.GeneralOperations;
import com.xogame.dao.InitGameDao;
import com.xogame.model.GameSetting.GAME_STATE;
import com.xogame.operations.game.GameOperations;
import com.xogame.model.IniteGame;
import com.xogame.repositry.FacadeRepositry;

@Service
public class GameService implements CommandLineRunner, InitGameDao, GeneralOperations<IniteGame> {

	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(GameService.class);

	@Autowired
	private FacadeRepositry facadeRepositry;

	@Autowired
	private GameOperations gameOperations;

	public GameService() {
		super();
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@Override
	public IniteGame save(IniteGame t) {
		return facadeRepositry.getGameRepositry().save(t);
	}

	@Override
	public IniteGame update(UUID id, IniteGame t) {

		IniteGame dbGame = findById(id);

		dbGame = gameOperations.gameInProgress(dbGame, t);

		return save(dbGame);
	}

	public IniteGame update(IniteGame game) {
		return save(game);
	}

	@Override
	public void delete(IniteGame t) {
		facadeRepositry.getGameRepositry().delete(t);
	}

	@Override
	public void delete(UUID id) {
		facadeRepositry.getGameRepositry().deleteById(id);
	}

	@Override
	public IniteGame findById(UUID id) {
		Optional<IniteGame> game = facadeRepositry.getGameRepositry().findById(id);
		if (game.isPresent())
			return game.get();
		return null;
	}

	@Override
	public List<IniteGame> findAll() {
		return facadeRepositry.getGameRepositry().findAll();
	}

	// get watting game in server ...
	public List<IniteGame> waitStateGame() {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameSetting.gameState").all(GAME_STATE.WATTING.name()));
		return facadeRepositry.getMongoTemplate().find(query, IniteGame.class);
	}

	// get name og game in server ...
	public IniteGame getGameAsName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameSetting.gameName").all(name));
		return facadeRepositry.getMongoTemplate().findOne(query, IniteGame.class);
	}

}
