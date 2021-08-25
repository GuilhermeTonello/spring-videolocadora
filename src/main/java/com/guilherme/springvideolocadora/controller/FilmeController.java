package com.guilherme.springvideolocadora.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.guilherme.springvideolocadora.model.Filme;
import com.guilherme.springvideolocadora.repository.CategoriaRepository;
import com.guilherme.springvideolocadora.repository.FilmeRepository;

@Controller
@RequestMapping("filme")
public class FilmeController {
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar(@RequestParam(name = "busca", required = false) String busca, 
			@PageableDefault(sort = "titulo") Pageable paginacao) {
		ModelAndView mav = new ModelAndView("paginas/filme/filme-lista");
		if (busca != null) {
			mav.addObject("filmes", filmeRepository.buscarFilmesPorTitulo(busca, paginacao));
			mav.addObject("busca", busca);
		} else {
			mav.addObject("filmes", filmeRepository.findAll(paginacao));
			mav.addObject("busca", "");
		}
		return mav;
	}
	
	@GetMapping("cadastrar")
	public ModelAndView paginaCadastrar() {
		ModelAndView mav = new ModelAndView("paginas/filme/filme-formulario");
		mav.addObject("categorias", categoriaRepository.findAll());
		mav.addObject("filme", new Filme());
		return mav;
	}
	
	@PostMapping("salvar")
	public ModelAndView salvarFilme(@Valid Filme filme, BindingResult result, RedirectAttributes atributos) {
		if (filme.getId().isBlank()) {
			filme.setEstoqueAtual(filme.getEstoqueTotal());
		} else {
			Optional<Filme> optFilmeNoBancoDeDados = filmeRepository.findById(filme.getId());
			if (!optFilmeNoBancoDeDados.isPresent()) {
				atributos.addFlashAttribute("erro_nao_encontrado", "Filme " + filme.getId() + " não encontrado!");
				return redirectToLista();
			}
			Filme filmeNoBancoDeDados = optFilmeNoBancoDeDados.get();
			Integer alugado = filmeNoBancoDeDados.getEstoqueTotal() - filmeNoBancoDeDados.getEstoqueAtual();
			if (filme.getEstoqueTotal() - alugado >= 0) {
				filme.setEstoqueAtual(filme.getEstoqueTotal() - alugado);
			} else
				result.addError(new ObjectError("filme", "Esse Estoque Total causará um Estoque Atual negativo!"));
		}
		Optional<Filme> optFilme = filmeRepository.findByTitulo(filme.getTitulo());
		if (optFilme.isPresent() && !optFilme.get().getId().equals(filme.getId())) {
			result.addError(new ObjectError("filme", "Esse Filme já existe!"));
		}
		if (result.hasErrors()) {
			return paginaCadastrar()
						.addObject("erros", result.getAllErrors())
						.addObject("filme", filme);
		}
		filmeRepository.save(filme);
		atributos.addFlashAttribute("mensagem_sucesso", "Filme " + filme.getTitulo() + " salvo!");
		return redirectToLista();
	}
	
	@GetMapping("editar/{id}")
	public ModelAndView editarFilme(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Filme> filme = filmeRepository.findById(id);
		if (!filme.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Filme " + id + " não encontrado!");
			return redirectToLista();
		}
		return paginaCadastrar()
					.addObject("filme", filmeRepository.findById(id));
	}
	
	@GetMapping("deletar/{id}")
	public ModelAndView deletarFilme(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Filme> filme = filmeRepository.findById(id);
		if (filme.isPresent()) {
			String filmeNome = filme.get().getTitulo();
			filmeRepository.deleteById(id);
			atributos.addFlashAttribute("mensagem_sucesso", "Filme " + filmeNome + " deletado!");
		} else {
			atributos.addFlashAttribute("erro_nao_encontrado", "Filme " + id + " não encontrado!");
		}
		return redirectToLista();
	}
	
	private ModelAndView redirectToLista() {
		return new ModelAndView("redirect:/filme/listar");
	}
	
}
