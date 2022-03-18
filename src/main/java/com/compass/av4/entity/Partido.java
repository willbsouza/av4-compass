package com.compass.av4.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compass.av4.controller.dto.PartidoDTO;
import com.compass.av4.entity.enums.Ideologia;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Partido {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty @NotNull
	private String nomeDoPartido;
	
	@NotEmpty @NotNull
	private String sigla;
	
	@NotNull @Enumerated(EnumType.STRING)
	private Ideologia ideologia;
	
	@NotNull @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDeFundacao;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Associado> associados = new ArrayList<Associado>();
	
	public Partido() {}
	
	public Partido(PartidoDTO partidoDTO) {
		this.id = partidoDTO.getId();
		this.nomeDoPartido = partidoDTO.getNomeDoPartido();
		this.sigla = partidoDTO.getSigla();
		this.ideologia = partidoDTO.getIdeologia();
		this.dataDeFundacao = partidoDTO.getDataDeFundacao();
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

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
	public void addAssociado(Associado associado) {
		associados.add(associado);
	}
	public void removeAssociado(Associado associado) {
		associados.remove(associado);
	}
	
	public boolean procurarAssociado(Associado associado) {
		return associados.contains(associado);
	}
	
	public PartidoDTO converter(Partido partido) {
		return new PartidoDTO(partido);
	}
}
