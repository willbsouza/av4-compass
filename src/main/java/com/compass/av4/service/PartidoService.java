package com.compass.av4.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compass.av4.controller.dto.AssociadoComPartidoDTO;
import com.compass.av4.controller.dto.PartidoDTO;
import com.compass.av4.entity.Associado;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.Ideologia;
import com.compass.av4.repository.PartidoRepository;
import com.compass.av4.service.exception.DeletePartidoException;
import com.compass.av4.service.exception.EntityNotFoundException;
import com.compass.av4.service.exception.MethodArgumentNotValidException;

@Service
public class PartidoService {

	@Autowired
	private PartidoRepository partidoRepository;

	public List<PartidoDTO> findByIdeologia(Ideologia ideologia) {
		return partidoRepository.findByIdeologia(ideologia);
	}

	public List<PartidoDTO> findAll() {
		List<Partido> partidos = partidoRepository.findAll();
		List<PartidoDTO> partidosDTO = partidos.stream().map(a -> a.converter(a)).collect(Collectors.toList());
		return partidosDTO;
	}

	public List<AssociadoComPartidoDTO> findByPartidoAssociados(Integer id) {
		Partido partido = findByPartido(id);
		List<Associado> associados = partido.getAssociados();
		List<AssociadoComPartidoDTO> associadosDTO = associados.stream().map(a -> a.converter(a, partido))
				.collect(Collectors.toList());
		return associadosDTO;
	}

	public PartidoDTO findById(Integer id) {
		Partido partido = findByPartido(id);
		return new PartidoDTO(partido);
	}

	public PartidoDTO save(@Valid PartidoDTO partidoDTO) {
		Partido partido = new Partido(partidoDTO);
		try {
			partidoRepository.save(partido);
			return new PartidoDTO(partido);
		} catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}

	public PartidoDTO updateById(Integer id, @Valid PartidoDTO partidoDTO) {
		Partido partidoParaAtualizar = findByPartido(id);
		try {
			partidoParaAtualizar.setNomeDoPartido(partidoDTO.getNomeDoPartido());
			partidoParaAtualizar.setSigla(partidoDTO.getSigla());
			partidoParaAtualizar.setIdeologia(partidoDTO.getIdeologia());
			partidoParaAtualizar.setDataDeFundacao(partidoDTO.getDataDeFundacao());
			return new PartidoDTO(partidoParaAtualizar);
		} catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}

	public void deleteById(Integer id) {
		List<Associado> associados = findByPartido(id).getAssociados();
		if(associados.isEmpty()) {
			partidoRepository.deleteById(id);
		} else {
			throw new DeletePartidoException("Partido contém associados. Para excluir o partido é necessário desvincular os associados primeiro.");
		}
	}
	
	private Partido findByPartido(Integer id) {
		Partido partido = partidoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ID " + id + " não encontrado."));
		return partido;
	}
}
