package com.guilherme.springvideolocadora.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.guilherme.springvideolocadora.model.Locacao;

public interface LocacaoRepository extends MongoRepository<Locacao, String> {
	
	@Query(value = "{ dataLocacao: {$ne:null} }", sort = "{ dataLocacao: -1 }")
	Page<Locacao> findUltimasLocacoes(Pageable pageable);
	
	@Query(value = "{ dataDevolucao: {$ne:null} }", sort = "{ dataDevolucao: -1 }")
	Page<Locacao> findUltimasDevolucoes(Pageable pageable);
	
	Page<Locacao> findByClienteCpf(String clienteCpf, Pageable pageable);
	
}
