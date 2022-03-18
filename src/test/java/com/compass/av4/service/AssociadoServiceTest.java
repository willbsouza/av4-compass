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

import com.compass.av4.controller.dto.AssociacaoDTO;
import com.compass.av4.controller.dto.AssociadoComPartidoDTO;
import com.compass.av4.entity.Associado;
import com.compass.av4.entity.Partido;
import com.compass.av4.entity.enums.CargoPolitico;
import com.compass.av4.entity.enums.Ideologia;
import com.compass.av4.entity.enums.Sexo;
import com.compass.av4.repository.AssociadoRepository;
import com.compass.av4.repository.PartidoRepository;
import com.compass.av4.service.exception.EntityNotFoundException;
import com.compass.av4.service.exception.MethodArgumentNotValidException;

@SpringBootTest
public class AssociadoServiceTest {

	@InjectMocks
	private AssociadoService service;
	
	@Mock
	private AssociadoRepository repository;
	
	@Mock
	private PartidoRepository partidoRepository;
	
	@Mock
	private Associado associado;
	
	@Mock
	private AssociacaoDTO associacaoDTO;
	
	@Mock
	private AssociadoComPartidoDTO associadoComPartidoDTO;
	
	private Optional<Associado> optionalAssociado;
	
	private Optional<Partido> optionalPartido;
	
	@Mock
	private Partido partido;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startAssociado();
		startAssociadoComPartidoDTO();
		startAssociacaoDTO();
		startOptionalAssociado();
		startPartido();
		startOptionalPartido();
	}

	@Test
	void deveriaRetornarUmaListaDeAssociadoAoPassarCargoPolitico() {
		Mockito.when(repository.findByCargoPolitico(Mockito.any())).thenReturn(List.of(associado));
		List<Associado> response = service.findByCargoPolitico(CargoPolitico.PRESIDENTE);
		
		assertNotNull(response);
		assertEquals(Associado.class, response.get(0).getClass());
		assertEquals(1, response.get(0).getId());
		assertEquals("NomeTeste", response.get(0).getNome());
		assertEquals(CargoPolitico.PRESIDENTE, response.get(0).getCargoPolitico());
		assertEquals(Sexo.FEMININO, response.get(0).getSexo());
	}
	
	@Test
	void deveriaRetornarUmaListaDeAssociadoFindAll() {
		Mockito.when(repository.findAll()).thenReturn(List.of(associado));
		List<Associado> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(Associado.class, response.get(0).getClass());
		assertEquals(1, response.get(0).getId());
		assertEquals("NomeTeste", response.get(0).getNome());
		assertEquals(CargoPolitico.PRESIDENTE, response.get(0).getCargoPolitico());
		assertEquals(Sexo.FEMININO, response.get(0).getSexo());
	}
	
	@Test
	void deveriaRetornarUmAssociadoAoPassarId() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalAssociado);
		Associado response = service.findById(1);
		
		assertNotNull(response);
		assertEquals(Associado.class, response.getClass());
		assertEquals(1, response.getId());
		assertEquals("NomeTeste", response.getNome());
		assertEquals(CargoPolitico.PRESIDENTE, response.getCargoPolitico());
		assertEquals(Sexo.FEMININO, response.getSexo());
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
		Mockito.when(repository.findById(Mockito.anyInt()))
		.thenThrow(new MethodArgumentNotValidException("Dados inválidos."));
		try {
			service.updateById(1, associado);
		} catch(MethodArgumentNotValidException e) {
			assertEquals(MethodArgumentNotValidException.class, e.getClass());
			assertEquals("Dados inválidos.", e.getMessage());
		}
	}
	
	@Test
	void deveriaCriarUmNovoAssociado() {
		Mockito.when(repository.save(Mockito.any())).thenReturn(associado);
		
		Associado response = service.save(associado);
		
		assertNotNull(response);
		assertEquals(Associado.class, response.getClass());
		assertEquals(1, response.getId());
		assertEquals("NomeTeste", response.getNome());
		assertEquals(CargoPolitico.PRESIDENTE, response.getCargoPolitico());
		assertEquals(Sexo.FEMININO, response.getSexo());
	}
	
	@Test
	void deveriaAssociarAUmPartidoERetornarUmAssociadoComPartidoDTO() {
		Mockito.when(partidoRepository.findById(Mockito.anyInt())).thenReturn(optionalPartido);
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalAssociado);
	
		AssociadoComPartidoDTO response = service.associarPartido(associacaoDTO);
		
		assertNotNull(response);
		assertEquals(AssociadoComPartidoDTO.class, response.getClass());
		assertEquals(1, response.getId());
		assertEquals("NomeTeste", response.getNome());
		assertEquals(CargoPolitico.PRESIDENTE, response.getCargoPolitico());
	}
	
	@Test
	void deveriaDeletarUmAssociadoComSucesso() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalAssociado);
		Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
		service.deleteById(1);
		
		Mockito.verify(repository, times(1)).deleteById(Mockito.anyInt());
	}

	private void startOptionalAssociado() {
		associado = new Associado();
		associado.setId(1);
		associado.setNome("NomeTeste");
		associado.setCargoPolitico(CargoPolitico.PRESIDENTE);
		associado.setSexo(Sexo.FEMININO);
		associado.setDataDeNascimento(LocalDate.now());
		optionalAssociado = Optional.of(associado);
	}

	private void startAssociado() {
		associado = new Associado();
		associado.setId(1);
		associado.setNome("NomeTeste");
		associado.setCargoPolitico(CargoPolitico.PRESIDENTE);
		associado.setSexo(Sexo.FEMININO);
		associado.setDataDeNascimento(LocalDate.now());
	}
	
	private void startAssociadoComPartidoDTO() {
		associadoComPartidoDTO = new AssociadoComPartidoDTO();
		associadoComPartidoDTO.setId(1);
		associadoComPartidoDTO.setNome("NomeTeste");
		associadoComPartidoDTO.setCargoPolitico(CargoPolitico.PRESIDENTE);
		associadoComPartidoDTO.setNomePartido("NomeTeste");
	}
	
	private void startAssociacaoDTO() {
		associacaoDTO = new AssociacaoDTO();
		associacaoDTO.setIdAssociado(1);
		associacaoDTO.setIdPartido(1);
	}
	
	private void startPartido() {
		partido = new Partido();
		partido.setId(1);
		partido.setNomeDoPartido("NomeTeste");
		partido.setSigla("NT");
		partido.setDataDeFundacao(LocalDate.now());
		partido.setIdeologia(Ideologia.ESQUERDA);
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
}
