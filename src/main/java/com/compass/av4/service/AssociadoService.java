package com.compass.av4.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
				.orElseThrow(() -> new EntityNotFoundException("ID " + id + " não encontrado "));
	}

	public Associado save(@Valid AssociadoDTO associadoDTO) {
		Partido partido = partidoRepository.findById(associadoDTO.getIdPartido()).orElse(null);
		try {
			Associado associado = new Associado();
			associado.setNome(associadoDTO.getNome());
			associado.setCargoPolitico(associadoDTO.getCargoPolitico());
			associado.setDataDeNascimento(associadoDTO.getDataDeNascimento());
			associado.setSexo(associadoDTO.getSexo());
			associado.setPartido(partido);
			return associadoRepository.save(associado);
		} catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}

	public Associado updateById(Integer id, @Valid AssociadoDTO associadoDTO) {
		Partido partido = partidoRepository.findById(associadoDTO.getIdPartido()).orElse(null);
		Associado associadoParaAtualizar = findById(id);
		try {
			associadoParaAtualizar.setNome(associadoDTO.getNome());
			associadoParaAtualizar.setCargoPolitico(associadoDTO.getCargoPolitico());
			associadoParaAtualizar.setDataDeNascimento(associadoDTO.getDataDeNascimento());
			associadoParaAtualizar.setSexo(associadoDTO.getSexo());
			associadoParaAtualizar.setPartido(partido);
			return associadoParaAtualizar;
		} catch (MethodArgumentNotValidException e) {
			throw new MethodArgumentNotValidException(e.getMessage());
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		associadoRepository.deleteById(id);
	}
}
