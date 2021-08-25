package com.guilherme.springvideolocadora.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import com.guilherme.springvideolocadora.model.Cliente;
import com.guilherme.springvideolocadora.model.Filme;
import com.guilherme.springvideolocadora.model.Locacao;
import com.guilherme.springvideolocadora.repository.ClienteRepository;
import com.guilherme.springvideolocadora.repository.FilmeRepository;
import com.guilherme.springvideolocadora.repository.LocacaoRepository;

@Controller
@RequestMapping("locacao")
public class LocacaoController {
	
	@Autowired
	private LocacaoRepository locacaoRepository;
	
	@Autowired 
	private FilmeRepository filmeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar(@RequestParam(name = "busca", required = false) String busca,
			@PageableDefault(sort = "dataLocacao", direction = Direction.DESC) Pageable paginacao) {
		ModelAndView mav = new ModelAndView("paginas/locacao/locacao-lista");
		if (busca != null) {
			mav.addObject("locacoes", locacaoRepository.findByClienteCpf(busca, paginacao));
			mav.addObject("busca", busca);
		} else {
			mav.addObject("locacoes", locacaoRepository.findAll(paginacao));
			mav.addObject("busca", "");
		}
		return mav;
	}
	
	@GetMapping("cadastrar")
	public ModelAndView paginaCadastrar() {
		ModelAndView mav = new ModelAndView("paginas/locacao/locacao-formulario");
		mav.addObject("locacao", new Locacao());
		mav.addObject("filmes", filmeRepository.procurarFilmesDisponiveis());
		return mav;
	}
	
	@PostMapping("salvar")
	public ModelAndView salvarLocacao(@Valid Locacao locacao, BindingResult result, RedirectAttributes atributos) {
		Optional<Cliente> cliente = clienteRepository.findByCpf(locacao.getClienteCpf());
		if (!cliente.isPresent()) {
			result.addError(new ObjectError("locacao", "Cliente " + locacao.getClienteCpf() + " não está cadastrado!"));
		}
		if (result.hasErrors()) {
			return paginaCadastrar()
					.addObject("locacao", locacao)
					.addObject("erros", result.getAllErrors());
		}
		
		Filme filme = filmeRepository.findByTitulo(locacao.getFilme()).get();
		
		if (!locacao.getId().isBlank()) {
			Optional<Locacao> optLocacaoNoBancoDeDados = locacaoRepository.findById(locacao.getId());
			if (optLocacaoNoBancoDeDados.isPresent()) {
				
				Locacao locacaoNoBancoDeDados = optLocacaoNoBancoDeDados.get();
				Filme filmeNoBancoDeDados = filmeRepository.findByTitulo(locacaoNoBancoDeDados.getFilme()).get();
				
				locacao.setDataLocacao(locacaoNoBancoDeDados.getDataLocacao());
				if (locacaoNoBancoDeDados.getDataDevolucao() != null) {
					locacao.setDataDevolucao(locacaoNoBancoDeDados.getDataDevolucao());
				}
				if (!locacaoNoBancoDeDados.getFilme().equals(locacao.getFilme())
						&& locacao.getDataDevolucao() == null) {
					filmeNoBancoDeDados.setEstoqueAtual(filmeNoBancoDeDados.getEstoqueAtual() + 1);
					filmeRepository.save(filmeNoBancoDeDados);
					
					filme.setEstoqueAtual(filme.getEstoqueAtual() - 1);
					filmeRepository.save(filme);
				}
			} else {
				atributos.addFlashAttribute("erro_nao_encontrado", "Locação " + locacao.getId() + " não encontrada!");
				return redirectToLista();
			}
		} else {
			locacao.setDataLocacao(LocalDateTime.now());
			filme.setEstoqueAtual(filme.getEstoqueAtual() - 1);
			filmeRepository.save(filme);
		}
		
		locacaoRepository.save(locacao);
		atributos.addFlashAttribute("mensagem_sucesso", "Locação de " + locacao.getClienteCpf() + " salva!");
		return redirectToLista();
	}
	
	@GetMapping("devolver/{id}")
	public ModelAndView devolverLocacao(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Locacao> optLocacao = locacaoRepository.findById(id);
		if (!optLocacao.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Locação " + id + " não encontrada!");
			return redirectToLista();
		}
		Locacao locacao = optLocacao.get();
		if (locacao.getDataDevolucao() != null) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Locação " + id + " já foi devolvida!");
			return redirectToLista();
		}
		
		Filme filme = filmeRepository.findByTitulo(locacao.getFilme()).get();
		filme.setEstoqueAtual(filme.getEstoqueAtual() + 1);
		filmeRepository.save(filme);
		
		locacao.setDataDevolucao(LocalDateTime.now());
		locacaoRepository.save(locacao);
		atributos.addFlashAttribute("mensagem_sucesso", "Locação " + id + " devolvida!");
		return redirectToLista();
	}
	
	@GetMapping("editar/{id}")
	public ModelAndView editarCategoria(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Locacao> locacao = locacaoRepository.findById(id);
		if (!locacao.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Locação " + id + " não encontrada!");
			return redirectToLista();
		}
		return paginaCadastrar()
				.addObject("locacao", locacao.get());
	}
	
	@GetMapping("deletar/{id}")
	public ModelAndView deletarLocacao(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Locacao> locacao = locacaoRepository.findById(id);
		if (!locacao.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Locação " + id + " não encontrada!");
			return redirectToLista();
		}
		
		if (locacao.get().getDataDevolucao() == null) {
			Filme filme = filmeRepository.findByTitulo(locacao.get().getFilme()).get();
			filme.setEstoqueAtual(filme.getEstoqueAtual() + 1);
			filmeRepository.save(filme);
		}
		
		String locacaoId = locacao.get().getId();
		locacaoRepository.deleteById(id);
		atributos.addFlashAttribute("mensagem_sucesso", "Locação " + locacaoId + " deletada!");
		return redirectToLista();
	}
	
	private ModelAndView redirectToLista() {
		return new ModelAndView("redirect:/locacao/listar");
	}

}
