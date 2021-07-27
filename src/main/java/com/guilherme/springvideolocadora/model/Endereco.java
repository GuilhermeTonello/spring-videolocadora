package com.guilherme.springvideolocadora.model;

import lombok.Data;

@Data
public class Endereco {
	
	private String cep;
	
	private String cidade;
	
	private String rua;
	
	private String bairro;
	
	private String complemento;
	
}
