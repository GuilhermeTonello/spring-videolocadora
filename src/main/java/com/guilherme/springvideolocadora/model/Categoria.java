package com.guilherme.springvideolocadora.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "categorias")
@Data
public class Categoria {
	
	@Id
	private String id;

	private String nome;
	
}
