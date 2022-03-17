package com.compass.av4.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compass.av4.controller.dto.AssociacaoPartidoDTO;
import com.compass.av4.controller.dto.AssociadoDTO;
import com.compass.av4.entity.Associado;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.CargoPolitico;
import com.compass.av4.repository.AssociadoRepository;
import com.compass.av4.repository.PartidoRepository;
import com.compass.av4.service.exception.EntityNotFoundException;
import com.compass.av4.service.exception.MethodArgumentNotValidException;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private PartidoRepository partidoRepository;
	
	public List<Associado> findByCargoPolitico(CargoPolitico cargo) {
		return associadoRepository.findByCargoPolitico(cargo);
	}

	public List<Associado> findAll() {
		return associadoRepository.findAll();
	}

	public Associado findById(Integer id) {
		return associadoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ID " + id + " não encontrado."));
	}

	public Associado save(@Valid AssociadoDTO associadoDTO) {
		try {
			Associado associado = new Associado();
			associado.setNome(associadoDTO.getNome());
			associado.setCargoPolitico(associadoDTO.getCargoPolitico());
			associado.setDataDeNascimento(associadoDTO.getDataDeNascimento());
			associado.setSexo(associadoDTO.getSexo());
			return associadoRepository.save(associado);
		} catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}
	
	public Associado associarPartido(@Valid AssociacaoPartidoDTO associacaoPartidoDTO) {
		Partido partido = partidoRepository.findById(associacaoPartidoDTO.getIdPartido())
				.orElseThrow(() -> new EntityNotFoundException("Partido com ID " + associacaoPartidoDTO.getIdPartido() + " não encontrado."));
		Associado associado = associadoRepository.findById(associacaoPartidoDTO.getIdAssociado())
				.orElseThrow(() -> new EntityNotFoundException("Associado com ID " + associacaoPartidoDTO.getIdAssociado() + " não encontrado."));
		partido.addAssociado(associado);
		return associado;
	}

	public Associado updateById(Integer id, @Valid AssociadoDTO associadoDTO) {
		Associado associadoParaAtualizar = findById(id);
		try {
			associadoParaAtualizar.setNome(associadoDTO.getNome());
			associadoParaAtualizar.setCargoPolitico(associadoDTO.getCargoPolitico());
			associadoParaAtualizar.setDataDeNascimento(associadoDTO.getDataDeNascimento());
			associadoParaAtualizar.setSexo(associadoDTO.getSexo());
			return associadoParaAtualizar;
		} catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		associadoRepository.deleteById(id);
	}

	public void deletarAssociacao(Integer idAssociado, Integer idPartido) {
		Associado associado = associadoRepository.findById(idAssociado)
				.orElseThrow(() -> new EntityNotFoundException("Associado com ID " + idAssociado + " não encontrado."));
		Partido partido = partidoRepository.findById(idPartido)
				.orElseThrow(() -> new EntityNotFoundException("Partido com ID " + idPartido + " não encontrado."));
		if (partido.procurarAssociado(associado)) {
			partido.removeAssociado(associado);
		}else {
			throw new EntityNotFoundException("Associado com ID " + idAssociado + " não encontrado no Partido com ID " + idPartido);	
		}
	}
}
