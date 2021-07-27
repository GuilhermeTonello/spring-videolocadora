package com.guilherme.springvideolocadora.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.guilherme.springvideolocadora.model.Locacao;

public interface LocacaoRepository extends MongoRepository<Locacao, String> {

}
