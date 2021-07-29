package com.guilherme.springvideolocadora.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "clientes")
@Data
public class Cliente {
	
	@Id
	private String id;
	
	@NotBlank(message = "O Nome não pode estar vazio.")
	@Size(max = 70, message = "O máximo de caracteres para o Nome deve ser 70.")
	private String nome;

	@Indexed(unique = true)
	@NotBlank(message = "O CPF não pode estar vazio.")
	@CPF(message = "CPF inválido.")
	private String cpf;
	
	@NotBlank(message = "O Telefone não pode estar vazio.")
	@Size(max = 20, message = "O máximo de caracteres para o Telefone deve ser 20.")
	private String telefone;
	
	@Valid
	private Endereco endereco;

}
