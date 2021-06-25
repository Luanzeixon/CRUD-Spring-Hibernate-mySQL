package br.edu.ifrn.crud.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;


@Controller
@RequestMapping("/usuarios")
public class cadastroUsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Usuario()); //envia uma variavel usuario que corresponde a um novo usuario em branco que sera defenida na pagina html.
		return "usuario/cadastro";
	}
	
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvar(Usuario usuario,  RedirectAttributes attr, 
				HttpSession sesao) {
			
		//Ja serve para cadastro e edicao
		usuarioRepository.save(usuario);
		
		attr.addFlashAttribute("msgSucesso","Opera��o realizada com sucesso");
		
		return "redirect:/usuarios/cadastro"; //usar redirect na url usuarios, para "apagar"(atualizar) os contatos salvos do form (tem que colocar /)
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/editar/{id}") //na url vai ta o parametro que deseja editar informado no html, o path-variable pega essa variavel de mesmo nome 
	public String iniciarEdicao(
			@PathVariable("id") Integer idUsuario, ModelMap model,
			HttpSession sessao
			) {
		
		List<Usuario> usuariosCadastrados =  
				(List<Usuario>) sessao.getAttribute("usuariosCadastrados");
		
		Usuario u = new Usuario(); // criar um objeto pra buscar o id entre os usuarios cadastrados
		u.setId(idUsuario); //setar o id da url no usuario criado 
		
		int pos = usuariosCadastrados.indexOf(u); // indexof pega a posição de um objeto na lista
		u = usuariosCadastrados.get(pos); // u recebe o usuario que tem o mesmo id na mesma posição
		
		model.addAttribute("usuario", u);  // enviar o usuario para pagina html que no form de cadastro vai ser setado esse u como usuario
		
		
		return "/usuario/cadastro";
	}
	
	@ModelAttribute("profissoes")
	public List<String> getProfissoes(){ //Lista de string com todas as profissões retornadas no html
		return Arrays.asList("Professor","Medico","Advogado","Bombeiro","Policial","Outro");
	}
		
}
