package com.guilherme.springvideolocadora.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "filmes")
@Data
public class Filme {
	
	@Id
	private String id;
	
	private String titulo;
	
	private String sinopse;
	
	private Double valor;
	
	private Set<String> categorias;
	
}
