package com.guilherme.springvideolocadora.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "categorias")
@Data
public class Categoria {
	
	@Id
	private String id;

	@Indexed(unique = true)
	@NotBlank(message = "O nome da categoria não pode estar vazio.")
	@Size(max = 30, message = "O máximo de caracteres deve ser 30.")
	private String nome;
	
}
