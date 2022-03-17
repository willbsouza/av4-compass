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
import com.compass.av4.controller.dto.PartidoDTO;
import com.compass.av4.entity.enums.Ideologia;
import com.compass.av4.service.PartidoService;

@RestController
@RequestMapping("/partidos")
public class PartidoController {

	@Autowired
	private PartidoService partidoService;
	
	@GetMapping
	public ResponseEntity<List<PartidoDTO>> findAllOrByIdeologia(Ideologia ideologia) {
		if(ideologia != null) {
			return ResponseEntity.ok(partidoService.findByIdeologia(ideologia));
		}
		return ResponseEntity.ok(partidoService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PartidoDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(partidoService.findById(id));
	}
	
	@GetMapping("/{id}/associados")
	public ResponseEntity<List<AssociadoComPartidoDTO>> findByPartidoAssociados(@PathVariable Integer id){
		return ResponseEntity.ok(partidoService.findByPartidoAssociados(id));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<PartidoDTO> save(@RequestBody @Valid PartidoDTO partidoDTO, UriComponentsBuilder uriBuilder){
		URI uri = uriBuilder.path("/partidos/{id}").buildAndExpand(partidoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(partidoService.save(partidoDTO));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PartidoDTO> updateById(@PathVariable Integer id, @RequestBody @Valid PartidoDTO partidoDTO){
		return ResponseEntity.ok(partidoService.updateById(id, partidoDTO));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		partidoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
