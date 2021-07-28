package com.guilherme.springvideolocadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilherme.springvideolocadora.model.Categoria;
import com.guilherme.springvideolocadora.repository.CategoriaRepository;

@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar() {
		ModelAndView mav = new ModelAndView("paginas/categoria/categoria-lista");
		mav.addObject("categorias", categoriaRepository.findAll());
		return mav;
	}
	
	@GetMapping({"cadastrar"})
	public ModelAndView paginaCadastro() {
		ModelAndView mav = new ModelAndView("paginas/categoria/categoria-formulario");
		mav.addObject("categoria", new Categoria());
		return mav;
	}
	
	@PostMapping("salvar")
	public ModelAndView salvarCategoria(Categoria categoria) {
		categoriaRepository.save(categoria);
		return redirectToLista();
	}
	
	@GetMapping("editar/{id}")
	public ModelAndView editarCategoria(@PathVariable("id") String id) {
		return paginaCadastro()
				.addObject("categoria", categoriaRepository.findById(id).get());
	}
	
	@GetMapping("deletar/{id}")
	public ModelAndView deletarCategoria(@PathVariable("id") String id) {
		categoriaRepository.deleteById(id);
		return redirectToLista();
	}
	
	private ModelAndView redirectToLista() {
		return new ModelAndView("redirect:/categoria/listar");
	}

}
