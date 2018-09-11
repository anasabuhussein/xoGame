package com.xogame.repositry;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.xogame.model.Messages;

@Repository
public interface MessagesRepositry extends MongoRepository<Messages, UUID> {

}
