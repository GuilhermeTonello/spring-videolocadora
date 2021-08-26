package com.guilherme.springvideolocadora.seeder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.guilherme.springvideolocadora.model.Usuario;
import com.guilherme.springvideolocadora.repository.UsuarioRepository;

@Configuration
public class UsuarioSeeder {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	private void seeder() {
		usuarioRepository.deleteAll();
		
		List<Usuario> usuarios = new ArrayList<>();
		
		Usuario admin = new Usuario(null, "Administrador", "admin", passwordEncoder.encode("admin123"), new String[] {"ROLE_ADMIN"});
		usuarios.add(admin);
		
		Usuario funcionario = new Usuario(null, "Funcionário", "funcionario", passwordEncoder.encode("func123"), new String[] {"ROLE_FUNCIONARIO"});
		usuarios.add(funcionario);
		
		Usuario estagiario = new Usuario(null, "Estagiário", "estagiario", passwordEncoder.encode("estag123"), new String[] {"ROLE_ESTAGIARIO"});
		usuarios.add(estagiario);
		
		usuarioRepository.saveAll(usuarios);
	}
	
}
