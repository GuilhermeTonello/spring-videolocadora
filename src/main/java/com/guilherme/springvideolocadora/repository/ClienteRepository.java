package com.guilherme.springvideolocadora.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.guilherme.springvideolocadora.model.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

}
