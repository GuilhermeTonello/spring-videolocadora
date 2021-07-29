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

import com.guilherme.springvideolocadora.model.Categoria;
import com.guilherme.springvideolocadora.repository.CategoriaRepository;

@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar(
			@RequestParam(name = "busca", required = false) String busca,
			@PageableDefault(sort = "nome") Pageable paginacao) {
		ModelAndView mav = new ModelAndView("paginas/categoria/categoria-lista");
		if (busca != null) {
			mav.addObject("categorias", categoriaRepository.findByNomeIgnoreCaseContaining(busca, paginacao));
			mav.addObject("busca", busca);
		} else {
			mav.addObject("categorias", categoriaRepository.findAll(paginacao));
			mav.addObject("busca", "");
		}
		return mav;
	}
	
	@GetMapping({"cadastrar"})
	public ModelAndView paginaCadastro() {
		ModelAndView mav = new ModelAndView("paginas/categoria/categoria-formulario");
		mav.addObject("categoria", new Categoria());
		return mav;
	}
	
	@PostMapping("salvar")
	public ModelAndView salvarCategoria(@Valid Categoria categoria, BindingResult result, RedirectAttributes atributos) {
		Optional<Categoria> consultaCategoriaExistente = categoriaRepository.findByNome(categoria.getNome());
		if (consultaCategoriaExistente.isPresent() && !consultaCategoriaExistente.get().getId().equals(categoria.getId())) {
			result.addError(new ObjectError("categoria", "Essa categoria já existe!"));
		}
		if (result.hasErrors()) {
			return paginaCadastro()
						.addObject("categoria", categoria)
						.addObject("erros", result.getAllErrors());
		}
		categoriaRepository.save(categoria);
		atributos.addFlashAttribute("mensagem_sucesso", "Categoria " + categoria.getNome() + " salva!");
		return redirectToLista();
	}
	
	@GetMapping("editar/{id}")
	public ModelAndView editarCategoria(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		if (!categoria.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Categoria " + id + " não encontrada!");
			return redirectToLista();
		}
		return paginaCadastro()
				.addObject("categoria", categoria.get());
	}
	
	@GetMapping("deletar/{id}")
	public ModelAndView deletarCategoria(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		if (!categoria.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Categoria " + id + " não encontrada!");
			return redirectToLista();
		}
		String categoriaNome = categoria.get().getNome();
		categoriaRepository.deleteById(id);
		atributos.addFlashAttribute("mensagem_sucesso", "Categoria " + categoriaNome + " deletada!");
		return redirectToLista();
	}
	
	private ModelAndView redirectToLista() {
		return new ModelAndView("redirect:/categoria/listar");
	}

}
