package br.edu.ifrn.crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.crud.service.UsuarioService;

//DAR PERMISSÃO
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService service;
	
	//RESPONSAVEL PELA CONFIG DE AUTENTICAÇÃO E AUTORIZAÇÃO
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		//PERMITIR  ACESSAR TODAS AS PAGINAS SEM PRECISAR DE LOGIN
		http.authorizeRequests().anyRequest().permitAll();
		*/
		
		//PERMITIR PASTAS A ACESSAR A PAGINA, ** SIGNIFICA ALGO DENTRO DA PASTA...
		
		http.authorizeRequests()
			.antMatchers("/css/**,/imagens/**,/js/**").permitAll()
			.antMatchers("/publico/**").permitAll()
			
			.anyRequest().authenticated() //O RESTO SO SE FOR AUTENTICADO
			.and() //ADICIONAR OUTRA CONDIÇÃO(OPÇOES)
				.formLogin()
				.loginPage("/login") //PAGINA QUE VAI CORRESPONDER AO LOGIN QUE FOI CRIADA(TIRAR A PAGINA QUE E FEITA AUTOMATICAMENTE)
				.defaultSuccessUrl("/",true) //SE O LOGIN DER CERTO CHAMA A URL "/" (A PAGINA INICIAL DO PROJETO)
				.failureUrl("/login-error") //SE O LOGIN NAO DER CERTO CHAMAR UMA PAGINA COM URL DE ERROR
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout") //URL PASSADA NO FORM DA PAGINA INICIO.HTML (LOGOUT É FEITO AUTOMATICAMETE PELO THYMELEAF)
				.logoutSuccessUrl("/") //SE O LOGOUT BEM SUCEDIDO VAI PRA PAGINA INICIAL
			.and()
				.rememberMe(); //PERMITIR QUE O CHECKBOX NO HTML(URL = LOGIN) FUNCIONE
				
		//... SE ALGUMA URL NO CONTROLLER TIVER COMO EX: "/PUBLICO/INICIO" VAI SER LIBERADA O ACESSO SEM LOGIN
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//DIZENDO PRO SPRING QUE A SENHA DO USUARIO VAI ESTAR CRIPOGRAFADA COM A CRIPTOGRAFIA USADA ANTERIORMNETE 
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
