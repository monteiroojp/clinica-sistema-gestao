package dev.monteiroojp.clinica.modelo.servico;

import dev.monteiroojp.clinica.modelo.usuario.Medico;
import dev.monteiroojp.clinica.modelo.valueObject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServicoTeste {

    private Servico servico;

    @BeforeEach
    void setup() {
        // Criando um serviço de Cardiologia como base
        servico = new Servico(1L, "  Eletrocardiograma  ", new BigDecimal("150.00"), 30, "Cardiologista");
    }

    @Test
    @DisplayName("Deve armazenar nome e ocupação normalizados (trim e lowercase)")
    void deveArmazenarDadosNormalizados() {
        assertEquals("Eletrocardiograma", servico.getName());
        assertEquals("cardiologista", servico.getOcupacaoNecessaria());
    }

    @Test
    @DisplayName("Deve permitir qualquer médico se a ocupação necessária for vazia")
    void devePermitirQualquerMedicoSeOcupacaoVazia() {
        Servico servicoGeral = new Servico(2L, "Consulta Geral", new BigDecimal("100.00"), 20, null);

        // Mock simples de um médico (usando o construtor que definimos antes)
        Medico medico = criarMedicoMock("Pediatra");

        assertTrue(servicoGeral.podeSerRealizadoPor(medico));
    }

    @Test
    @DisplayName("Deve validar se a ocupação do médico é compatível com o serviço")
    void deveValidarOcupacaoDoMedico() {
        Medico cardiologista = criarMedicoMock("  Cardiologista  ");
        Medico ortopedista = criarMedicoMock("Ortopedista");

        assertTrue(servico.podeSerRealizadoPor(cardiologista), "Deveria aceitar cardiologista");
        assertFalse(servico.podeSerRealizadoPor(ortopedista), "Deveria rejeitar ortopedista");
    }

    @Test
    @DisplayName("Deve rejeitar valores e durações inválidos no construtor")
    void deveRejeitarParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () ->
                new Servico(2L, "Erro", new BigDecimal("-10.00"), 30, "Clinico"));

        assertThrows(IllegalArgumentException.class, () ->
                new Servico(2L, "Erro", new BigDecimal("100.00"), 0, "Clinico"));

        assertThrows(IllegalArgumentException.class, () ->
                new Servico(2L, "", new BigDecimal("100.00"), 30, "Clinico"));
    }

    @Test
    @DisplayName("Não deve permitir alterar o ID uma vez que já foi definido")
    void naoDevePermitirAlterarId() {
        assertThrows(IllegalStateException.class, () -> servico.setId(2L));
    }

    @Test
    @DisplayName("Deve identificar serviços iguais pelo ID")
    void deveIdentificarServicosIguaisPeloId() {
        Servico outroServico = new Servico(1L, "Outro Nome", new BigDecimal("500.00"), 60, "Outra");
        assertEquals(servico, outroServico);
        assertEquals(servico.hashCode(), outroServico.hashCode());
    }

    private Medico criarMedicoMock(String ocupacao) {
        return new Medico(
                "Doutor Teste",
                new Cpf("123.456.789-00"),
                new Email("doc@teste.com"),
                new Telefone("(11) 99999-9999"),
                LocalDate.parse("1980-01-01"),
                'M',
                "senha123",
                new BigDecimal("5000.00"),
                LocalDate.now(),
                new Crm("CRM/SP 123456"),
                ocupacao);
    }
}