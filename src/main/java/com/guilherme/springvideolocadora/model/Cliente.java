package com.guilherme.springvideolocadora.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "clientes")
@Data
public class Cliente {
	
	@Id
	private String id;
	
	private String nome;
	
	private String cpf;
	
	private String telefone;
	
	private Endereco endereco;

}
