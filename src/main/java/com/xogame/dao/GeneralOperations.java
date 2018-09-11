package com.xogame.dao;

import java.util.List;
import java.util.UUID;

public interface GeneralOperations<T> {

	public T save(T t);

	public T update(UUID id, T t);

	public void delete(T t);

	public void delete(UUID id);

	public T findById(UUID id);

	public List<T> findAll();
}
