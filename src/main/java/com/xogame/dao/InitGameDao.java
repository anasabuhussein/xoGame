package com.xogame.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xogame.model.IniteGame;

/**
 * This class for increase the operations that gets data from database for
 * {@link IniteGame}.
 * 
 * @author Anas Abu-Hussein
 * @since 11/9/2018
 *
 **/

public interface InitGameDao extends GeneralOperations<IniteGame> {

	public List<IniteGame> waitStateGame();

	public IniteGame getGameAsName(String name);

}
