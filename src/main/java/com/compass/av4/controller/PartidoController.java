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

import com.compass.av4.controller.dto.AssociadoComPartidoDTO;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.Ideologia;
import com.compass.av4.service.PartidoService;

@RestController
@RequestMapping("/partidos")
public class PartidoController {

	@Autowired
	private PartidoService partidoService;
	
	@GetMapping
	public ResponseEntity<List<Partido>> findAllOrByIdeologia(Ideologia ideologia) {
		if(ideologia != null) {
			return ResponseEntity.ok(partidoService.findByIdeologia(ideologia));
		}
		return ResponseEntity.ok(partidoService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Partido> findById(@PathVariable Integer id){
		return ResponseEntity.ok(partidoService.findById(id));
	}
	
	@GetMapping("/{id}/associados")
	public ResponseEntity<List<AssociadoComPartidoDTO>> findByPartidoAssociados(@PathVariable Integer id){
		return ResponseEntity.ok(partidoService.findByPartidoAssociados(id));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Partido> save(@RequestBody @Valid Partido partido, UriComponentsBuilder uriBuilder){
		URI uri = uriBuilder.path("/partidos/{id}").buildAndExpand(partido.getId()).toUri();
		return ResponseEntity.created(uri).body(partidoService.save(partido));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Partido> updateById(@PathVariable Integer id, @RequestBody @Valid Partido partido){
		return ResponseEntity.ok(partidoService.updateById(id, partido));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		partidoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
