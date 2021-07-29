package com.guilherme.springvideolocadora.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.guilherme.springvideolocadora.model.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {
	
	Optional<Categoria> findByNome(String nome);
	
	@Query(value = "{ 'nome': {$regex: /.*?0.*/, $options: 'i'} }")
	Page<Categoria> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
	
}
