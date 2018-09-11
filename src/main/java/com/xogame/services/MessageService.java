package com.xogame.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.xogame.dao.GeneralOperations;
import com.xogame.model.Messages;

@Service
public class MessageService implements GeneralOperations<Messages>{

	@Override
	public Messages save(Messages t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Messages update(UUID id, Messages t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Messages t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Messages findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Messages> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
