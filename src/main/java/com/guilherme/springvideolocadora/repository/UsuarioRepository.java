package com.guilherme.springvideolocadora.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.guilherme.springvideolocadora.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	
	Optional<Usuario> findByLogin(String login);
	
	@Query(value = "{$or:[ {'nome': {$regex: /.*?0.*/, $options: 'i'} }, "
			+ "{'login': {$regex: /.*?1.*/, $options: 'i'} } ]}")
	Page<Usuario> findByNomeOrLoginIgnoreCaseContaining(String nome, String login, Pageable pageable);

}
