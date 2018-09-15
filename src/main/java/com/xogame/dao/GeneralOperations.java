package com.xogame.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

/**
 * This class general operations that gets data from database for general
 * object.
 * 
 * @author Anas Abu-Hussein
 * @since 11/9/2018
 *
 **/
public interface GeneralOperations<T> {

	public T save(T t);

	public T update(UUID id, T t);

	public void delete(T t);

	public void delete(UUID id);

	public T findById(UUID id);

	public List<T> findAll();
}
