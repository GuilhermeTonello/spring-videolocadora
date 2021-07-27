package com.guilherme.springvideolocadora.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "locacoes")
@Data
public class Locacao {
	
	@Id
	private String id;
	
	private String cliente;
	
	private List<String> filmes;
	
	private Double valor;
	
	private LocalDateTime dataLocacao;
	
	private LocalDateTime dataDevolucao;

}
