package com.guilherme.springvideolocadora.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.guilherme.springvideolocadora.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	
	Optional<Usuario> findByLogin(String login);

}
