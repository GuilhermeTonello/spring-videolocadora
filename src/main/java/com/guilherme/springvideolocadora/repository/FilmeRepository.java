package com.guilherme.springvideolocadora.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.guilherme.springvideolocadora.model.Filme;

public interface FilmeRepository extends MongoRepository<Filme, String> {
	
	@Query(value = "{ 'titulo': {$regex: /.*?0.*/, $options: 'i'} }")
	Page<Filme> buscarFilmesPorTitulo(String titulo, Pageable pageable);

	@Query(value = "{ 'estoqueAtual': {$gt: 0} }", fields = "{ 'titulo': true, 'valor': true }")
	List<Filme> procurarFilmesDisponiveis();
	
	Optional<Filme> findByTitulo(String titulo);

}
