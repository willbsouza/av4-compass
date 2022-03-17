package com.compass.av4.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.compass.av4.controller.dto.AssociacaoPartidoDTO;
import com.compass.av4.controller.dto.AssociadoDTO;
import com.compass.av4.entity.Associado;
import com.compass.av4.entity.enums.CargoPolitico;
import com.compass.av4.service.AssociadoService;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

	@Autowired
	private AssociadoService associadoService;
	
	@GetMapping
	public ResponseEntity<List<Associado>> findAllOrByCargoPolitico(CargoPolitico cargo) {
		if(cargo != null) {
			return ResponseEntity.ok(associadoService.findByCargoPolitico(cargo));
		}
		return ResponseEntity.ok(associadoService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Associado> findById(@PathVariable Integer id){
		return ResponseEntity.ok(associadoService.findById(id));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Associado> save(@RequestBody @Valid AssociadoDTO associadoDTO, UriComponentsBuilder uriBuilder){
		URI uri = uriBuilder.path("/associados/{id}").buildAndExpand(associadoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(associadoService.save(associadoDTO));
	}
	
	@PostMapping("/partidos")
	@Transactional
	public ResponseEntity<Associado> associarPartido(@RequestBody @Valid AssociacaoPartidoDTO associacaoPartidoDTO, UriComponentsBuilder uriBuilder){
		URI uri = uriBuilder.path("/associados/{id}").buildAndExpand(associacaoPartidoDTO.getIdAssociado()).toUri();
		return ResponseEntity.created(uri).body(associadoService.associarPartido(associacaoPartidoDTO));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Associado> updateById(@PathVariable Integer id, @RequestBody @Valid AssociadoDTO associadoDTO){
		return ResponseEntity.ok(associadoService.updateById(id, associadoDTO));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		associadoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{idAssociado}/partidos/{idPartido}")
	@Transactional
	public ResponseEntity<?> deletarAssociacao(@PathVariable Integer idAssociado, @PathVariable Integer idPartido){
		associadoService.deletarAssociacao(idAssociado, idPartido);
		return ResponseEntity.noContent().build();
	}
}
