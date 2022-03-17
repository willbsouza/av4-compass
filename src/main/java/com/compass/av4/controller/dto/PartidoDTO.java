package com.compass.av4.controller.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.Ideologia;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PartidoDTO {

	private Integer id;
	
	@NotEmpty @NotNull
	private String nomeDoPartido;
	
	@NotEmpty @NotNull
	private String sigla;
	
	@NotNull @Enumerated(EnumType.STRING)
	private Ideologia ideologia;
	
	@NotNull @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDeFundacao;
	
	public PartidoDTO() {}

	public PartidoDTO(Partido partido) {
		this.id = partido.getId();
		this.nomeDoPartido = partido.getNomeDoPartido();
		this.sigla = partido.getSigla();
		this.ideologia = partido.getIdeologia();
		this.dataDeFundacao = partido.getDataDeFundacao();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeDoPartido() {
		return nomeDoPartido;
	}

	public void setNomeDoPartido(String nomeDoPartido) {
		this.nomeDoPartido = nomeDoPartido;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Ideologia getIdeologia() {
		return ideologia;
	}

	public void setIdeologia(Ideologia ideologia) {
		this.ideologia = ideologia;
	}

	public LocalDate getDataDeFundacao() {
		return dataDeFundacao;
	}

	public void setDataDeFundacao(LocalDate dataDeFundacao) {
		this.dataDeFundacao = dataDeFundacao;
	}
}
