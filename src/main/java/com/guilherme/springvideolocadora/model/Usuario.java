package com.guilherme.springvideolocadora.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	
	@Id
	private String id;
	
	private String nome;
	
	@Indexed(unique = true)
	private String login;
	
	private String senha;
	
	private String[] roles;

}
