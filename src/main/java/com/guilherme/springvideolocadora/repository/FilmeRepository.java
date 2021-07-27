package com.guilherme.springvideolocadora.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.guilherme.springvideolocadora.model.Filme;

public interface FilmeRepository extends MongoRepository<Filme, String> {

}
