package com.guilherme.springvideolocadora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar() {
		ModelAndView mav = new ModelAndView("paginas/categoria/categoria-lista");
		return mav;
	}
	
	@GetMapping({"cadastrar"})
	public ModelAndView paginaCadastro() {
		ModelAndView mav = new ModelAndView("paginas/categoria/categoria-formulario");
		return mav;
	}

}
