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
	
	@Transactional(readOnly = false)
	@GetMapping("/buscar")
	public String buscar(
			@RequestParam(name="nome", required = false) String nome, // pegar valor da url cujo o nome é "nome" e atribuir a uma String nome
			@RequestParam(name="email", required = false) String email,
			@RequestParam(name="mostrarTodosDados", required = false) Boolean mostrarTodosDados,
			ModelMap model
				// required = false: para que seja opcional a informação do parametro, se nao tiver isso da erro se nao informar
			) {
		
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
			HttpSession sessao, RedirectAttributes attr 
			) {
		
		List<Usuario> usuariosCadastrados = 
				(List<Usuario>) sessao.getAttribute("usuariosCadastrados");
		
		Usuario u = new Usuario(); //representa o mesmo usuario que quero remover
		u.setId(idUsuario);
		
			// so consegue remover graças a o hasCode e ao Equals(classe usuario, pois o id de "u" é igual ao que quer remover)
		boolean removeu = usuariosCadastrados.remove(u);
		
		if(removeu) {
			attr.addFlashAttribute("msgSucesso", "Usuário removido com sucesso!");
		}else {
			attr.addFlashAttribute("msgErro", "Não foi possivel remover o usuário");
		}
			
		return "redirect:/usuarios/buscar";
	}
	
}
