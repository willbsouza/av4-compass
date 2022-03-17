package com.compass.av4.controller.dto;

import javax.validation.constraints.NotNull;

public class AssociacaoPartidoDTO {
	
	@NotNull
	private Integer idAssociado;
	@NotNull
	private Integer idPartido;
	
	public Integer getIdAssociado() {
		return idAssociado;
	}
	public void setIdAssociado(Integer idAssociado) {
		this.idAssociado = idAssociado;
	}
	public Integer getIdPartido() {
		return idPartido;
	}
	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
}
