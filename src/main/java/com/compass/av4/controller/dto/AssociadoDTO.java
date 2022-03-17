package com.compass.av4.controller.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compass.av4.entity.enums.CargoPolitico;
import com.compass.av4.entity.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AssociadoDTO {
	
	private Integer id;
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @Enumerated(EnumType.STRING)
	private CargoPolitico cargoPolitico;
	
	@NotNull @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDeNascimento;
	
	@NotNull @Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	private Integer idPartido;

	public Integer getId() {
		return id;
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

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
}
