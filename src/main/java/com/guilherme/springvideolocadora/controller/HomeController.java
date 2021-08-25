package com.guilherme.springvideolocadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilherme.springvideolocadora.repository.LocacaoRepository;

@Controller
public class HomeController {
	
	@Autowired
	private LocacaoRepository locacaoRepository;
	
	@GetMapping({"", "home", "index"})
	public ModelAndView paginaHome() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("ultimasLocacaoes", locacaoRepository.findUltimasLocacoes(PageRequest.of(0, 5)));
		mav.addObject("ultimasDevolucoes", locacaoRepository.findUltimasDevolucoes(PageRequest.of(0, 5)));
		return mav;
	}
	
}
