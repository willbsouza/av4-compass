package com.compass.av4.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.compass.av4.controller.dto.AssociadoComPartidoDTO;
import com.compass.av4.controller.dto.PartidoDTO;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.CargoPolitico;
import com.compass.av4.entity.enums.Ideologia;
import com.compass.av4.repository.PartidoRepository;
import com.compass.av4.service.exception.EntityNotFoundException;
import com.compass.av4.service.exception.MethodArgumentNotValidException;


@SpringBootTest
public class PartidoServiceTest{
	
	@InjectMocks
	private PartidoService service;
	
	@Mock
	private PartidoRepository repository;
	
	@Mock
	private Partido partido;
	
	@Mock
	private PartidoDTO partidoDTO;
	
	private Optional<Partido> optionalPartido;
	
	@Mock
	private AssociadoComPartidoDTO associadoDTO;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startPartido();
		startPartidoDTO();
		startOptionalPartido();
		startAssociadoDTO();
	}

	@Test
	void deveriaRetornarUmaListaDePartidoAoPassarIdeologia() {
		Mockito.when(repository.findByIdeologia(Mockito.any())).thenReturn(List.of(partidoDTO));
		
		List<PartidoDTO> response = service.findByIdeologia(Ideologia.ESQUERDA);
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(PartidoDTO.class, response.get(0).getClass());
		assertEquals(1, response.get(0).getId());
		assertEquals("NomeTeste", response.get(0).getNomeDoPartido());
		assertEquals("NT", response.get(0).getSigla());
		assertEquals(Ideologia.ESQUERDA, response.get(0).getIdeologia());
	}
	
	@Test
	void deveriaRetornarUmaListaDePartidoFindAll() {
		Mockito.when(repository.findAll()).thenReturn(List.of(partido));
		
		List<PartidoDTO> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(PartidoDTO.class, response.get(0).getClass());
		assertEquals(1, response.get(0).getId());
		assertEquals("NomeTeste", response.get(0).getNomeDoPartido());
		assertEquals("NT", response.get(0).getSigla());
		assertEquals(Ideologia.ESQUERDA, response.get(0).getIdeologia());
	}
	
	@Test
	void deveriaRetornarUmPartidoFindById() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalPartido);
		
		PartidoDTO response = service.findById(partido.getId());
		
		assertNotNull(response);
		assertEquals(PartidoDTO.class, response.getClass());
		assertEquals(1, response.getId());
		assertEquals("NomeTeste", response.getNomeDoPartido());
		assertEquals("NT", response.getSigla());
		assertEquals(Ideologia.ESQUERDA, response.getIdeologia());
	}
	
	@Test
	void deveriaRetornarEntityNotFoundException() {
		Mockito.when(repository.findById(Mockito.anyInt()))
		.thenThrow(new EntityNotFoundException("ID não encontrado."));
		
		try {
			service.findById(1);
		} catch(EntityNotFoundException e) {
			assertEquals(EntityNotFoundException.class, e.getClass());
			assertEquals("ID não encontrado.", e.getMessage());
		}
	}
	
	@Test
	void deveriaRetornarMethodArgumentNotValidException() {
		Mockito.when(repository.findById(Mockito.any()))
		.thenThrow(new MethodArgumentNotValidException("Dados inválidos."));
		
		try {
			service.updateById(1, partidoDTO);
		} catch(MethodArgumentNotValidException e) {
			assertEquals(MethodArgumentNotValidException.class, e.getClass());
			assertEquals("Dados inválidos.", e.getMessage());
		}
	}
	
	@Test
	void deveriaCriarUmNovoPartido() {
		Mockito.when(repository.save(Mockito.any())).thenReturn(partido);
		
		PartidoDTO response = service.save(partidoDTO);
		
		assertNotNull(response);
		assertEquals(PartidoDTO.class, response.getClass());
		assertEquals(1, response.getId());
		assertEquals("NomeTeste", response.getNomeDoPartido());
		assertEquals("NT", response.getSigla());
		assertEquals(Ideologia.ESQUERDA, response.getIdeologia());
	}
	
	@Test
	void deveriaDeletarPartidoComSucesso() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalPartido);
		Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
		service.deleteById(1);
		
		Mockito.verify(repository, times(1)).deleteById(Mockito.anyInt());
	}	
	
	@Test
	void deveriaRetornarEntityNotFoundExceptionAoTentarDeletar() {
		Mockito.when(repository.findById(Mockito.anyInt()))
		.thenThrow(new EntityNotFoundException("ID não encontrado."));
		try {
			service.deleteById(1);
		} catch(Exception e) {
			assertEquals(EntityNotFoundException.class, e.getClass());
			assertEquals("ID não encontrado.", e.getMessage());
		}
	}	
	
	private void startPartido() {
		partido = new Partido();
		partido.setId(1);
		partido.setNomeDoPartido("NomeTeste");
		partido.setSigla("NT");
		partido.setDataDeFundacao(LocalDate.now());
		partido.setIdeologia(Ideologia.ESQUERDA);
	}
	
	private void startPartidoDTO() {
		partidoDTO = new PartidoDTO();
		partidoDTO.setId(1);
		partidoDTO.setNomeDoPartido("NomeTeste");
		partidoDTO.setSigla("NT");
		partidoDTO.setDataDeFundacao(LocalDate.now());
		partidoDTO.setIdeologia(Ideologia.ESQUERDA);
	}
	
	private void startOptionalPartido() {
		partido = new Partido();
		partido.setId(1);
		partido.setNomeDoPartido("NomeTeste");
		partido.setSigla("NT");
		partido.setDataDeFundacao(LocalDate.now());
		partido.setIdeologia(Ideologia.ESQUERDA);
		optionalPartido = Optional.of(partido);
	}
	
	private void startAssociadoDTO() {
		associadoDTO = new AssociadoComPartidoDTO();
		associadoDTO.setId(1);
		associadoDTO.setNome("NomeTeste");
		associadoDTO.setCargoPolitico(CargoPolitico.PRESIDENTE);
		associadoDTO.setNomePartido("NomeTeste");
	}
}
	


