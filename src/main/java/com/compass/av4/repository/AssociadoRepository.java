package com.compass.av4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compass.av4.entity.Associado;
import com.compass.av4.entity.enums.CargoPolitico;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Integer>{

	List<Associado> findByCargoPolitico(CargoPolitico cargo);
	
}
