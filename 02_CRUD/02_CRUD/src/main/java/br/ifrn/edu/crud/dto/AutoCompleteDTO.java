package br.ifrn.edu.crud.dto;

public class AutoCompleteDTO {
	
	//CLASSE PARA AJUDAR A ENVIAR PRA PAGINA O NOME E O ID DA PROFISSAO, POR CAUSA DO JQUERY-UI
	
	private String label; //NOME PROFISSAO
	
	private Integer value; // ID

	//METODO QUE SERA CHAMADO QUANDO EU TIVER CONTRUINDO UM NOVO OBG DESSA CLASSE
	public AutoCompleteDTO(String label, Integer value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
