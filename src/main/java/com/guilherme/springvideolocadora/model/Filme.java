package com.guilherme.springvideolocadora.model;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "filmes")
@Data
public class Filme {
	
	@Id
	private String id;
	
	@NotBlank(message = "O Título não pode estar vazio.")
	@Size(max = 90, message = "O máximo de caracteres para o Nome deve ser 90.")
	@Indexed(unique = true)
	private String titulo;
	
	@NotBlank(message = "A Sinopse não pode estar vazia.")
	@Size(max = 255, message = "O máximo de caracteres para o Nome deve ser 255.")
	private String sinopse;
	
	@NotNull(message = "O Valor não pode estar vazio.")
	private Double valor;
	
	private Set<String> categorias;
	
	@NotNull(message = "O Estoque Total não pode estar vazio.")
	private Integer estoqueTotal;
	
	private Integer estoqueAtual;
	
}
