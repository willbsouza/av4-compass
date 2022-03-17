package com.compass.av4.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compass.av4.controller.dto.AssociadoComPartidoDTO;
import com.compass.av4.entity.Associado;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.Ideologia;
import com.compass.av4.repository.PartidoRepository;
import com.compass.av4.service.exception.EntityNotFoundException;
import com.compass.av4.service.exception.MethodArgumentNotValidException;

@Service
public class PartidoService {

	@Autowired
	private PartidoRepository partidoRepository;
	
	public List<Partido> findByIdeologia(Ideologia ideologia) {
		return partidoRepository.findByIdeologia(ideologia);
	}
	
	public List<AssociadoComPartidoDTO> findByPartidoAssociados(Integer id) {
		Partido partido = findById(id);
		List<Associado> associados = partido.getAssociados();
		List<AssociadoComPartidoDTO> associadosDTO = associados
				.stream().map(a -> a.converter(a, partido))
				.collect(Collectors.toList());
		return associadosDTO;
	}
	
	public List<Partido> findAll() {
		return partidoRepository.findAll();
	}

	public Partido findById(Integer id) {
		return partidoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ID " + id + " não encontrado."));
	}

	public Partido save(@Valid Partido partido) {
		try {
			return partidoRepository.save(partido);			
		}catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}

	public Partido updateById(Integer id, @Valid Partido partido) {
		Partido partidoParaAtualizar = findById(id);
		try {
			partidoParaAtualizar.setNomeDoPartido(partido.getNomeDoPartido());
			partidoParaAtualizar.setSigla(partido.getSigla());
			partidoParaAtualizar.setIdeologia(partido.getIdeologia());
			partidoParaAtualizar.setDataDeFundacao(partido.getDataDeFundacao());
			return partidoParaAtualizar;			
		}catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		partidoRepository.deleteById(id);
	}
}
