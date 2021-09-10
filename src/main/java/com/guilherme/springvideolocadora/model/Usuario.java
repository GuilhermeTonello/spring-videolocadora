package com.guilherme.springvideolocadora.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	
	@NotBlank(message = "O Nome não pode estar vazio.")
	@Size(max = 255, message = "O máximo de caracteres para o Nome deve ser 255.")
	private String nome;
	
	@Indexed(unique = true)
	@NotBlank(message = "O Login não pode estar vazio.")
	@Size(max = 30, message = "O máximo de caracteres para o Login deve ser 30.")
	private String login;
	
	@NotBlank(message = "A Senha não pode estar vazia.")
	@Size(max = 255, message = "O máximo de caracteres para a Senha deve ser 255.")
	private String senha;
	
	private String[] roles;

}
