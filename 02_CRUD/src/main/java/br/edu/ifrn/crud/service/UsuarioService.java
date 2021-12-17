package br.edu.ifrn.crud.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	
	//CLASSE SERVE PARA DIZER AO SPRING BUSCAR NO BD O USUARIO ENCONTRADO NA PAGINA, SE T� TUDO CERTO, PRA AUTENTICA-LO.
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// USERNAME � O MESMO NOME COLOCADO NO NOME DO INPUT DO EMAIL, NA PAGINA HTML LOGIN
		
		Usuario usuario = repository.findByEmail(username)
							.orElseThrow(() -> 
								new UsernameNotFoundException("Usuario n�o encontrado"));
		
		//PASSAR OS DADOS DO USUARIO PRO SPRING PRA VER SE A SENHA CRIPTOGRAFADA CORRESPONDE A SENHA DIGITADA NA PAGINA
		return new User(
				usuario.getEmail(),
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(usuario.getPerfil())
				);
	}
	
	
		
}
