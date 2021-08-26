package com.guilherme.springvideolocadora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@GetMapping("login")
	public ModelAndView paginaLogin() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
}
