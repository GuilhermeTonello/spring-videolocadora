package com.guilherme.springvideolocadora.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.guilherme.springvideolocadora.model.Filme;

public interface FilmeRepository extends MongoRepository<Filme, String> {
	
	@Query(value = "{ 'titulo': {$regex: /.*?0.*/, $options: 'i'} }")
	Page<Filme> buscarFilmesPorTitulo(String titulo, Pageable pageable);

}
