package com.xogame.dao;

import java.util.List;

import com.xogame.model.IniteGame;
import com.xogame.strategy_xo_game.GameStrategy;

public interface InitGameDao extends GeneralOperations<IniteGame> {

	public List<IniteGame> waitStateGame();

	public IniteGame getGameAsName(String name);

}
