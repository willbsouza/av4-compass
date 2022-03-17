package com.compass.av4.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compass.av4.entity.enums.Ideologia;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Partido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	@NotNull
	private String nomeDoPartido;
	
	@NotEmpty
	@NotNull
	private String sigla;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Ideologia ideologia;
	
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDeFundacao;
	
	@OneToMany(mappedBy = "partido")
	@JsonIgnore
	private List<Associado> associados;
	
	public Integer getId() {
		return id;
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
	public List<Associado> getAssociados() {
		return associados;
	}
	public void setAssociados(List<Associado> associados) {
		this.associados = associados;
	}
}
