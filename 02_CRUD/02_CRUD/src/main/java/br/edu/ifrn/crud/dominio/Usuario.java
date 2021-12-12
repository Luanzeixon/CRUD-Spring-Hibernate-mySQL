package br.edu.ifrn.crud.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // valor id preenchido automaticamente
	private int id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private String sexo;

	// Muitos usuarios podem ter uma profissao, opicional falso(obrigatorio)
	@ManyToOne(optional = false)
	private Profissao profissao;
	
	@ManyToMany
	private List<CursoFormacao> formacoes;

	@Transient
	private CursoFormacao cursoFormacao;

	// Relacionamento 1-1 (so uma pesso pode ter uma foto de perfil)
	// CascadeREMOVE = quando remover o usuario remove a foto tbm
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Arquivo foto;

	// pra que o java saiba diferenciar um usuario do outro pelo atributo id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public Arquivo getFoto() {
		return foto;
	}

	public void setFoto(Arquivo foto) {
		this.foto = foto;
	}

	public List<CursoFormacao> getFormacoes() {
		return formacoes;
	}

	public void setFormacoes(List<CursoFormacao> formacoes) {
		this.formacoes = formacoes;
	}

	public CursoFormacao getCursoFormacao() {
		return cursoFormacao;
	}

	public void setCursoFormacao(CursoFormacao cursoFormacao) {
		this.cursoFormacao = cursoFormacao;
	}
	

}
