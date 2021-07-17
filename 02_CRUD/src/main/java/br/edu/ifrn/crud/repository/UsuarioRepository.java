package br.edu.ifrn.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crud.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
	
	//Buscar pelo nome e pelo email
	@Query("select u from Usuario u where u.email like %:email%" //'%' serve para não precisar do email completo, e o ':' é porque é um parametro
			+ " and u.nome like %:nome%") //Consulta que será realizada pelo metodo
	List<Usuario> findByEmailAndNome(@Param("email") String email,
									@Param("nome") String nome);
	
	
	//BUSCAR PELO USERNAME = EMAIL
	@Query("select u from Usuario u where u.email like %:email%") 
	Optional<Usuario> findByEmail(@Param("email") String email);
		//OPTIONAL POIS PODE TER OU NÃO
	
}
