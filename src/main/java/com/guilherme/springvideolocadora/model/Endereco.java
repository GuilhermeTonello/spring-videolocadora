package com.guilherme.springvideolocadora.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Endereco {
	
	@NotBlank(message = "O CEP não pode estar vazio.")
	@Size(max = 15, message = "O máximo de caracteres para o CEP deve ser 15.")
	private String cep;
	
	@NotBlank(message = "A Cidade não pode estar vazia.")
	@Size(max = 30, message = "O máximo de caracteres para a Cidade deve ser 30.")
	private String cidade;
	
	@NotBlank(message = "A Rua não pode estar vazia.")
	@Size(max = 60, message = "O máximo de caracteres para a Rua deve ser 60.")
	private String rua;
	
	@NotBlank(message = "O Bairro não pode estar vazio.")
	@Size(max = 35, message = "O máximo de caracteres para o Bairro deve ser 35.")
	private String bairro;
	
	@NotBlank(message = "O Complemento não pode estar vazio.")
	@Size(max = 35, message = "O máximo de caracteres para o Complemento deve ser 35.")
	private String complemento;
	
}
