package com.guilherme.springvideolocadora.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guilherme.springvideolocadora.model.Usuario;
import com.guilherme.springvideolocadora.repository.UsuarioRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar(
			@RequestParam(name = "busca", required = false) String busca,
			@PageableDefault(sort = "nome") Pageable paginacao) {
		ModelAndView mav = new ModelAndView("paginas/usuario/usuario-lista");
		
		if (busca != null) {
			mav.addObject("usuarios", usuarioRepository.findByNomeOrLoginIgnoreCaseContaining(busca, busca, paginacao));
			mav.addObject("busca", busca);
		} else {
			mav.addObject("usuarios", usuarioRepository.findAll(paginacao));
			mav.addObject("busca", "");
		}
		
		return mav;
	}
	
	@GetMapping({"cadastrar"})
	public ModelAndView paginaCadastro() {
		ModelAndView mav = new ModelAndView("paginas/usuario/usuario-formulario");
		mav.addObject("usuario", new Usuario());
		return mav;
	}
	
	@PostMapping("salvar")
	public ModelAndView salvarCategoria(@Valid Usuario usuario, BindingResult result, RedirectAttributes atributos) {
		Optional<Usuario> consultaUsuarioExistente = usuarioRepository.findByLogin(usuario.getLogin());
		if (consultaUsuarioExistente.isPresent() && !consultaUsuarioExistente.get().getId().equals(usuario.getId())) {
			result.addError(new ObjectError("usuario", "Esse login já existe!"));
		}
		if (result.hasErrors()) {
			return paginaCadastro()
						.addObject("usuario", usuario)
						.addObject("erros", result.getAllErrors());
		}
		
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		
		if (usuario.getRoles().length == 0) {
			usuario.setRoles(new String[] { "ROLE_ESTAGIARIO" });
		}
		
		usuarioRepository.save(usuario);
		atributos.addFlashAttribute("mensagem_sucesso", "Usuário " + usuario.getNome() + "/" + usuario.getLogin() + " salvo!");
		return redirectToLista();
	}
	
	@GetMapping("editar/{id}")
	public ModelAndView editarCategoria(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (!usuario.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Usuário " + id + " não encontrado!");
			return redirectToLista();
		}
		return paginaCadastro()
				.addObject("usuario", usuario.get());
	}
	
	@GetMapping("deletar/{id}")
	public ModelAndView deletarCategoria(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (!usuario.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Usuário " + id + " não encontrado!");
			return redirectToLista();
		}
		String usuarioLogin = usuario.get().getLogin();
		usuarioRepository.deleteById(id);
		atributos.addFlashAttribute("mensagem_sucesso", "Usuário " + usuarioLogin + " deletado!");
		return redirectToLista();
	}
	
	private ModelAndView redirectToLista() {
		return new ModelAndView("redirect:/usuario/listar");
	}

}
