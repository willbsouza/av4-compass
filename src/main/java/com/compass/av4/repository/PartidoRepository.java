package com.compass.av4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compass.av4.controller.dto.PartidoDTO;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.Ideologia;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Integer>{

	List<PartidoDTO> findByIdeologia(Ideologia ideologia);
}
