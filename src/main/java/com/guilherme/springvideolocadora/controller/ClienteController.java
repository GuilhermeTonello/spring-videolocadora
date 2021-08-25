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

import com.guilherme.springvideolocadora.model.Cliente;
import com.guilherme.springvideolocadora.repository.ClienteRepository;

@Controller
@RequestMapping("cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping({"listar", ""})
	public ModelAndView paginaListar(@RequestParam(name = "busca", required = false) String busca,
			@PageableDefault(sort = "nome") Pageable paginacao) {
		ModelAndView mav = new ModelAndView("paginas/cliente/cliente-lista");
		if (busca != null) {
			mav.addObject("clientes", clienteRepository.procurarPorNomeCpfOuTelefone(busca, busca, busca, paginacao));
			mav.addObject("busca", busca);
		} else {
			mav.addObject("clientes", clienteRepository.findAll(paginacao));
			mav.addObject("busca", "");
		}
		return mav;
	}
	
	@GetMapping("cadastrar")
	public ModelAndView paginaCadastrar() {
		ModelAndView mav = new ModelAndView("paginas/cliente/cliente-formulario");
		mav.addObject("cliente", new Cliente());
		return mav;
	}
	
	@PostMapping("salvar")
	public ModelAndView salvarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributos) {
		cliente.setCpf(cliente.getCpf().replaceAll("[.]|[-]", ""));
		Optional<Cliente> optCliente = clienteRepository.findByCpf(cliente.getCpf());
		if (optCliente.isPresent() && !optCliente.get().getId().equals(cliente.getId())) {
			result.addError(new ObjectError("cliente", "Esse CPF já existe!"));
		}
		if (result.hasErrors()) {
			return paginaCadastrar()
						.addObject("erros", result.getAllErrors())
						.addObject("cliente", cliente);
		}
		clienteRepository.save(cliente);
		atributos.addFlashAttribute("mensagem_sucesso", "Cliente " + cliente.getNome() + " cliente!");
		return redirectToLista();
	}
	
	@GetMapping("editar/{id}")
	public ModelAndView editarCliente(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (!cliente.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Cliente " + id + " não encontrado!");
			return redirectToLista();
		}
		return paginaCadastrar()
					.addObject("cliente", cliente.get());
	}
	
	@GetMapping("deletar/{id}")
	public ModelAndView deletarCliente(@PathVariable("id") String id, RedirectAttributes atributos) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (!cliente.isPresent()) {
			atributos.addFlashAttribute("erro_nao_encontrado", "Cliente " + id + " não encontrado!");
			return redirectToLista();
		}
		String clienteNome = cliente.get().getNome();
		clienteRepository.deleteById(id);
		atributos.addFlashAttribute("mensagem_sucesso", "Cliente " + clienteNome + " deletado!");
		return redirectToLista();
	}
	
	private ModelAndView redirectToLista() {
		return new ModelAndView("redirect:/cliente/listar");
	}

}
