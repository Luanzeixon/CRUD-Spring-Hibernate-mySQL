package br.edu.ifrn.crud.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.CursoFormacao;
import br.edu.ifrn.crud.dominio.Profissao;
import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.CursoFormacaoRepository;
import br.edu.ifrn.crud.repository.ProfissaoRepository;
import br.edu.ifrn.crud.repository.UsuarioRepository;
import br.ifrn.edu.crud.dto.AutoCompleteDTO;


@Controller
@RequestMapping("/usuarios")
public class cadastroUsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	@Autowired
	private CursoFormacaoRepository formacaoRepository;
	
	@GetMapping("/cadastro")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Usuario()); //envia uma variavel usuario que corresponde a um novo usuario em branco que sera defenida na pagina html.
		return "usuario/cadastro";
	}
	
	@PostMapping("/salvar")
	@Transactional(readOnly = false) //POIS VAI ACESSAR O BD
	public String salvar(Usuario usuario,  RedirectAttributes attr, 
				HttpSession sesao) {
			
		//Ja serve para cadastro e edicao
		usuarioRepository.save(usuario);
		
		attr.addFlashAttribute("msgSucesso","Opera??????o realizada com sucesso");
		
		return "redirect:/usuarios/cadastro"; //usar redirect na url usuarios, para "apagar"(atualizar) os contatos salvos do form (tem que colocar /)
	}
	
	@GetMapping("/editar/{id}") //na url vai ta o parametro que deseja editar informado no html, o path-variable pega essa variavel de mesmo nome 
	public String iniciarEdicao(
			@PathVariable("id") Integer idUsuario, ModelMap model,
			HttpSession sessao
			) {
		
		Usuario u = usuarioRepository.findById(idUsuario).get(); // o get ??? pra retornar esse usuario	
		
		model.addAttribute("usuario", u);  // enviar o usuario para pagina html que no form de cadastro vai ser setado esse u como usuario
		
		
		return "/usuario/cadastro";
	}
	
	//METODO QUE RECEBE O TEXTO DO AUTOCOMPLETE
	@GetMapping("/autocompleteProfissoes")
	@Transactional(readOnly = true) //POIS VAI ACESSAR O BD, TRUE POIS NAO TERA ALTERA??????O NO BD
	@ResponseBody 					//PARA ENVIAR AS PROFISSOES EM UM FORMATO QUE O JQUERY UI ENTENTA
	public List<AutoCompleteDTO> autocompleteProfissoes(
			@RequestParam("term") String termo){
		
		List<Profissao> profissoes = profissaoRepository.findByNome(termo);
		
		//TEM QUE MANDAR A LISTA EM DTO PARA O JQUERY ENTENTA
		List<AutoCompleteDTO> resultados =  new ArrayList<>();
		
		//PERCORRER A LISTA
		profissoes.forEach(p -> resultados.add(
					new AutoCompleteDTO(p.getNome(),p.getId())
				));
		return resultados;
	}
	
	@GetMapping("/autocompleteFormacoes")
	@Transactional(readOnly = true) 
	@ResponseBody 					
	public List<AutoCompleteDTO> autocompleteFormacoes(
			@RequestParam("term") String termo){
		
		List<CursoFormacao> formacoes = formacaoRepository.findByNome(termo);
		
		
		List<AutoCompleteDTO> resultados =  new ArrayList<>();
		
	
		formacoes.forEach(p -> resultados.add(
					new AutoCompleteDTO(p.getNome(),p.getId())
				));
		return resultados;
	}
	
	@PostMapping("/addCursoFormacao")
	public String addEstudante(Usuario usuario, ModelMap model) {
		if(usuario.getFormacoes() == null) {
			usuario.setFormacoes(new ArrayList<>());
		}
		usuario.getFormacoes().add(usuario.getCursoFormacao());
		
		return "usuario/cadastro"; 
	}
	@PostMapping("/removerCursoFormacao/{id}")
	public String removerEstudante(@PathVariable("id") Integer idFormacao,
			Usuario usuario, 
			ModelMap model ) {
		
		CursoFormacao curso = new CursoFormacao();
		
		curso.setId(idFormacao);
		
		usuario.getFormacoes().remove(curso);
		
		return "turma/cadastroTurma"; 
	}
}
