package br.edu.ifrn.crud.controller;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class buscaUsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/busca")
	public String entrarBusca() {
		
		return "usuario/busca";
	}
	
	@Transactional(readOnly = false) //codigo fica mais explicito TEM que colocar isso
	@GetMapping("/buscar")
	public String buscar(
			@RequestParam(name="nome", required = false) String nome, // pegar valor da url cujo o nome é "nome" e atribuir a uma String nome
			@RequestParam(name="email", required = false) String email,
			@RequestParam(name="mostrarTodosDados", required = false) Boolean mostrarTodosDados,
			ModelMap model
				// required = false: para que seja opcional a informacao do parametro, se nao tiver isso da erro se nao informar
			) {
			
			//List pois podem ser mais de um usuario, ou seja, uma lista de usuarios
			List<Usuario> usuariosEncontrados = usuarioRepository.findByEmailAndNome(email, nome);
			
			//Enviar para a pagina html com o model(nome,conteudo)
			model.addAttribute("usuariosEncontrados", usuariosEncontrados);
		
		if(mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}
		
		return "usuario/busca";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/remover/{id}") 
	public String iniciarRemocao(	
			@PathVariable("id") Integer idUsuario, 
			 RedirectAttributes attr 
			) {
		
		
			usuarioRepository.deleteById(idUsuario);
			attr.addFlashAttribute("msgSucesso", "Usuario removido com sucesso");
		
			
		return "redirect:/usuarios/buscar";
	}
	
}
