package com.compass.av4.controller.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compass.av4.entity.Associado;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.CargoPolitico;

public class AssociadoComPartidoDTO {

	private Integer id;
	
	@NotNull @NotEmpty
	private String nome;
	@NotNull @Enumerated(EnumType.STRING)
	private CargoPolitico cargoPolitico;
	@NotNull @NotEmpty
	private String nomePartido;
	
	public AssociadoComPartidoDTO() {}
	
	public AssociadoComPartidoDTO(Associado associado, Partido partido) {
		this.setId(associado.getId());
		this.setNome(associado.getNome());
		this.setCargoPolitico(associado.getCargoPolitico());
		this.setNomePartido(partido.getNomeDoPartido());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public CargoPolitico getCargoPolitico() {
		return cargoPolitico;
	}
	public void setCargoPolitico(CargoPolitico cargoPolitico) {
		this.cargoPolitico = cargoPolitico;
	}
	public String getNomePartido() {
		return nomePartido;
	}
	public void setNomePartido(String nomePartido) {
		this.nomePartido = nomePartido;
	}
}
