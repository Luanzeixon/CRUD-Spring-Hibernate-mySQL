package br.edu.ifrn.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.crud.dominio.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
