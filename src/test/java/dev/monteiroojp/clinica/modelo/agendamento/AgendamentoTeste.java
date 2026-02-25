package dev.monteiroojp.clinica.modelo.agendamento;

import dev.monteiroojp.clinica.modelo.plano.Plano;
import dev.monteiroojp.clinica.modelo.servico.Servico;
import dev.monteiroojp.clinica.modelo.usuario.*;
import dev.monteiroojp.clinica.modelo.valueObject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AgendamentoTeste {

    private Paciente paciente;
    private Medico medico;
    private Servico servico;
    private Gestor gestor;
    private LocalDateTime amanha;

    @BeforeEach
    void setup() {
        amanha = LocalDateTime.now().plusDays(1);

        Plano plano = new Plano(1L, "UNIMED", new BigDecimal("0.2"), true);
        paciente = new Paciente("João", new Cpf("123.456.789-00"), new Email("j@t.com"),
                new Telefone("(12) 34567-8900"), LocalDate.parse("2000-01-01"),
                'M', "123", "", plano);

        medico = new Medico("Dr. Silva", new Cpf("987.654.321-00"), new Email("m@t.com"),
                new Telefone("(11) 88888-8888"), LocalDate.parse("1980-01-01"),
                'M', "123", new BigDecimal("5000"), LocalDate.now(),
                new Crm("CRM/SP 123456"), "Cardiologista");

        servico = new Servico(1L, "Eletro", new BigDecimal("200.00"), 30, "Cardiologista");

        gestor = new Gestor("Gestor Financeiro", new Cpf("123.456.789-00"), new Email("g@t.com"),
                new Telefone("(11) 77777-7777"), LocalDate.parse("1990-01-01"),
                'M', "123", new BigDecimal("3000"), LocalDate.now(), SetorGestor.FINANCEIRO);
    }

    // --- TESTES DE CONSTRUTOR E NULIDADE ---

    @Test
    @DisplayName("Deve rejeitar qualquer parâmetro nulo no construtor")
    void deveRejeitarParametrosNulosNoConstrutor() {
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(1L, null, medico, servico, amanha));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(1L, paciente, null, servico, amanha));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(1L, paciente, medico, null, amanha));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(1L, paciente, medico, servico, null));
    }

    @Test
    @DisplayName("Deve rejeitar data-hora no presente ou passado no construtor")
    void deveRejeitarDatasInvalidasNoConstrutor() {
        LocalDateTime passado = LocalDateTime.now().minusSeconds(1);
        LocalDateTime agora = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> new Agendamento(1L, paciente, medico, servico, passado));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(1L, paciente, medico, servico, agora));
    }

    // --- TESTES DE SETTERS E VALIDAÇÕES ---

    @Test
    @DisplayName("Deve validar setters contra valores nulos e datas inválidas")
    void deveValidarSetters() {
        Agendamento agendamento = new Agendamento(1L, paciente, medico, servico, amanha);

        assertThrows(IllegalArgumentException.class, () -> agendamento.setPaciente(null));
        assertThrows(IllegalArgumentException.class, () -> agendamento.setMedico(null));
        assertThrows(IllegalArgumentException.class, () -> agendamento.setServico(null));
        assertThrows(IllegalArgumentException.class, () -> agendamento.setDataHora(null));
        assertThrows(IllegalArgumentException.class, () -> agendamento.setDataHora(LocalDateTime.now().minusDays(1)));
    }

    @Test
    @DisplayName("Não deve permitir alterar ID já definido")
    void naoDevePermitirAlterarId() {
        Agendamento agendamento = new Agendamento(1L, paciente, medico, servico, amanha);
        assertThrows(IllegalStateException.class, () -> agendamento.setId(2L));
        assertThrows(IllegalArgumentException.class, () -> {
            Agendamento agendamentoSemId = new Agendamento(null, paciente, medico, servico, amanha);
            agendamentoSemId.setId(null);
        });
    }

    // --- TESTES DE CICLO DE VIDA (STATUS) ---

    @Test
    @DisplayName("Deve transitar status corretamente: PENDENTE -> CONFIRMADO -> CONCLUIDO")
    void deveSeguirCicloDeVidaCorreto() {
        Agendamento agendamento = new Agendamento(1L, paciente, medico, servico, amanha);
        assertEquals(StatusAgendamento.PENDENTE, agendamento.getStatus());

        agendamento.aprovar(gestor);
        assertEquals(StatusAgendamento.CONFIRMADO, agendamento.getStatus());
        assertEquals(gestor, agendamento.getAprovadoPor());

        agendamento.concluir();
        assertEquals(StatusAgendamento.CONCLUIDO, agendamento.getStatus());
    }

    @Test
    @DisplayName("Deve impedir transições de status inválidas")
    void deveImpedirTransicoesInvalidas() {
        Agendamento agendamento = new Agendamento(1L, paciente, medico, servico, amanha);

        // Tentar concluir sem confirmar
        assertThrows(IllegalArgumentException.class, agendamento::concluir);

        // Tentar aprovar o que já está confirmado
        agendamento.aprovar(gestor);
        assertThrows(IllegalArgumentException.class, () -> agendamento.aprovar(gestor));

        // Tentar cancelar o que já terminou
        agendamento.concluir();
        assertThrows(IllegalArgumentException.class, agendamento::cancelar);
    }

    // --- TESTES DE RESUMO MÉDICO (FEEDBACK) ---

    @Test
    @DisplayName("Deve permitir resumo médico apenas após conclusão e rejeitar vazios")
    void deveValidarRegrasDeFeedback() {
        Agendamento agendamento = new Agendamento(1L, paciente, medico, servico, amanha);

        // Tentativa antes da conclusão
        assertThrows(IllegalArgumentException.class, () -> agendamento.setFeedback("Paciente com dor."));

        agendamento.aprovar(gestor);
        agendamento.concluir();

        // Tentativas com valores inválidos
        assertThrows(IllegalArgumentException.class, () -> agendamento.setFeedback(null));
        assertThrows(IllegalArgumentException.class, () -> agendamento.setFeedback("    "));

        // Caso de sucesso
        agendamento.setFeedback("Paciente apresenta melhora no quadro clínico.");
        assertEquals("Paciente apresenta melhora no quadro clínico.", agendamento.getFeedback());
    }

    // --- TESTES FINANCEIROS ---

    @Test
    @DisplayName("Deve calcular corretamente o valor total, repasse do médico e da clínica")
    void deveCalcularValoresFinanceiros() {
        // Servico: 200.00 | Plano Desconto: 20% | Total: 160.00
        Agendamento agendamento = new Agendamento(1L, paciente, medico, servico, amanha);

        assertEquals(new BigDecimal("160.00"), agendamento.getValorTotal());
        // Médico: 160 * 0.6 = 96.00
        assertEquals(new BigDecimal("96.00"), agendamento.getValorMedico());
        // Clínica: 160 * 0.4 = 64.00
        assertEquals(new BigDecimal("64.00"), agendamento.getValorClinica());
    }
}