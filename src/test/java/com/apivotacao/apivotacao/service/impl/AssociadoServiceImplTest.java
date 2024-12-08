package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Associado;
import com.apivotacao.apivotacao.repository.AssociadoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AssociadoServiceImplTest {

    @Mock
    private AssociadoRepository repository;

    @InjectMocks
    private AssociadoServiceImpl associadoService;

    @Test
    void deveCriarUmAssociadoQuandoValido() {
        Associado associado = new Associado();
        associado.setId(1);
        associado.setNome("Matias Souza");
        associado.setCpf("12345678900");

        when(repository.existsByCpf(associado.getCpf())).thenReturn(false);
        when(repository.save(associado)).thenReturn(associado);

        Associado result = associadoService.create(associado);

        assertNotNull(result);
        assertEquals(associado.getNome(), result.getNome());
        assertEquals(associado.getCpf(), result.getCpf());

        verify(repository, times(1)).save(associado);
    }

    @Test
    void deveRetornarErroAoCriarAssocciadoComCpfJaRegistrado() {
        Associado associado = new Associado();
        associado.setId(1);
        associado.setNome("Matias Souza");
        associado.setCpf("12345678900");


        when(repository.existsByCpf(associado.getCpf())).thenReturn(true);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> {
            associadoService.create(associado);
        });

        assertEquals("Este cpf já está cadastrado em nossa base de dados!", result.getMessage());

        verify(repository, times(1)).existsByCpf(associado.getCpf());
        verify(repository, times(0)).save(associado);
    }

    @Test
    void deveRetornarErroAoCriarAssociadoComCpfVazio() {
        Associado associado = new Associado();
        associado.setId(1);
        associado.setNome("Matias Souza");
        associado.setCpf(null);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> {
            associadoService.create(associado);
        });

        assertEquals("O CPF deve ser preenchido!", result.getMessage());
        verify(repository, times(0)).save(associado);
    }

    @Test
    void deveRetornarErroAoCriarAssociadoComNomeVazio() {
        Associado associado = new Associado();
        associado.setId(1);
        associado.setCpf("12345678900");
        associado.setNome(null);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> {
            associadoService.create(associado);
        });

        assertEquals("O Nome deve ser preenchido!", result.getMessage());
        verify(repository, times(0)).save(associado);
    }
}