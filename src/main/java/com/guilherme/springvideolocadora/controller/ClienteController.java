package com.guilherme.springvideolocadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.guilherme.springvideolocadora.model.Cliente;
import com.guilherme.springvideolocadora.repository.ClienteRepository;

@Controller
@RequestMapping("cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar(@RequestParam(name = "busca", required = false, defaultValue = "") String busca,
			@PageableDefault(sort = "nome") Pageable paginacao) {
		ModelAndView mav = new ModelAndView("paginas/cliente/cliente-lista");
		mav.addObject("clientes", clienteRepository.findAll(paginacao));
		return mav;
	}
	
	@GetMapping("cadastrar")
	public ModelAndView paginaCadastrar() {
		ModelAndView mav = new ModelAndView("paginas/cliente/cliente-formulario");
		mav.addObject("cliente", new Cliente());
		return mav;
	}

}
