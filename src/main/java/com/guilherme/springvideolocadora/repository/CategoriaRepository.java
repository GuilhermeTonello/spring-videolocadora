package com.guilherme.springvideolocadora.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.guilherme.springvideolocadora.model.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {

}
