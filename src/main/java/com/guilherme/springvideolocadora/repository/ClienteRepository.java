package com.guilherme.springvideolocadora.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.guilherme.springvideolocadora.model.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
	
	@Query(value = "{$or:[ {'nome': {$regex: /.*?0.*/, $options: 'i'} }, "
			+ "{'cpf': {$regex: /.*?1.*/, $options: 'i'} },"
			+ "{'telefone': {$regex: /.*?1.*/, $options: 'i'} } ]}")
	Page<Cliente> procurarPorNomeCpfOuTelefone(String nome, String cpf, String telefone, Pageable pageable);

	Optional<Cliente> findByCpf(String cpf);
	
}
