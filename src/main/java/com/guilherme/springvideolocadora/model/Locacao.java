package com.guilherme.springvideolocadora.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "locacoes")
@Data
public class Locacao {
	
	@Id
	private String id;
	
	@NotBlank(message = "O CPF está vazio!")
	private String clienteCpf;
	
	private String filme;
	
	@NotNull(message = "O Valor está vazio!")
	private Double valor;
	
	private LocalDateTime dataLocacao;
	
	private LocalDateTime dataDevolucao;

}
