package com.guilherme.springvideolocadora.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.guilherme.springvideolocadora.model.Usuario;
import com.guilherme.springvideolocadora.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
		}
		UserDetailsImpl userDetails = new UserDetailsImpl(usuario.get());
		return userDetails;
	}

}
