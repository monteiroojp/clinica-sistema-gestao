package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.excecao.SaldoInsuficienteException;
import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Crm;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicoTest {

    private Medico medico;

    @BeforeEach
    void setup(){
        medico = new Medico(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'M',
                "senha123",
                new BigDecimal("8000.00"),
                LocalDate.parse("2026-01-01"),
                new Crm("CRM/MG 123456"),
                "  Cardiologista  ");
    }

    //Teste de métodos de inicialização correta
    @Test
    @DisplayName("Classe médico deve instanciar seu objeto com saldo nulo")
    void deveInstanciarComSaldoZero(){
        assertEquals(BigDecimal.ZERO, medico.getSaldo());
    }

    @Test
    @DisplayName("Medico deve instanciar seu objeto com ocupação normalizada")
    void deveInstanciarComOcupacaoNormalziada(){
        assertEquals("cardiologista", medico.getOcupacao());
    }

    //Teste de manipulação de saldo de forma planejada
    @Test
    @DisplayName("Medico deve ser capaz de adicionar saldo de forma esperada")
    void deveAdicionarSaldoCorretamente(){
        medico.adicionarSaldo(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("200.00"), medico.getSaldo());
    }

    @Test
    @DisplayName("Medico deve ser capaz de descontar saldo de forma esperada")
    void deveDescontarSaldoCorretamente(){
        medico.adicionarSaldo(new BigDecimal("200.00"));
        medico.descontarSaldo(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), medico.getSaldo());
    }

    //Calculo do salario total
    @Test
    @DisplayName("Classe Medico deve ser capaz de calcular salario total corretamente")
    void deveCalcularSalarioTotalCorretamente(){
        medico.adicionarSaldo(new BigDecimal("3000.00"));
        medico.setSalarioBase(new BigDecimal("9000.00"));
        assertEquals(new BigDecimal("12000.00"), medico.calcularSalarioTotal());
    }

    //Teste das exceptions
    @Test
    @DisplayName("Classe medico deve rejeitar crms null na instancia")
    void deveRejeitarCrmNull(){
            assertThrows(IllegalArgumentException.class, () -> new Medico(
                    "João Pedro",
                    new Cpf("123.456.789-00"),
                    new Email("emailteste@teste.com"),
                    new Telefone("(12) 34567-8900"),
                    LocalDate.parse("2000-01-01"),
                    'M',
                    "senha123",
                    new BigDecimal("8000.00"),
                    LocalDate.parse("2026-01-01"),
                    null,
                    "  Cardiologista  "));
    }

    @Test
    @DisplayName("Classe medico deve rejeitar ocupacoes nulas ou blanks na instancia e nos sets")
    void deveRejeitarOcupacaoNulaOuBlank(){
        assertThrows(IllegalArgumentException.class, () -> new Medico(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'M',
                "senha123",
                new BigDecimal("8000.00"),
                LocalDate.parse("2026-01-01"),
                new Crm("CRM/MG 123456"),
                null));

        assertThrows(IllegalArgumentException.class, () -> new Medico(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'M',
                "senha123",
                new BigDecimal("8000.00"),
                LocalDate.parse("2026-01-01"),
                new Crm("CRM/MG 123456"),
                "     "));

        assertThrows(IllegalArgumentException.class, () -> medico.setOcupacao(null));
        assertThrows(IllegalArgumentException.class, () -> medico.setOcupacao("    "));
    }

    @Test
    @DisplayName("Classe Medico deve rejeitar adicionar ou descontar saldos de valores negativos")
    void deveRejeitarAdicionarOuDescontarSaldoNegativo(){
        assertThrows(IllegalArgumentException.class, () -> medico.adicionarSaldo(new BigDecimal("-200.00")));
        assertThrows(IllegalArgumentException.class, () -> medico.descontarSaldo(new BigDecimal("-200.00")));
    }

    @Test
    @DisplayName("Classe medico deve rejeitar descontos de saldo maiores que o saldo atual")
    void deveRejeitarDescontosMaioresQueOSaldo(){
        medico.adicionarSaldo(new BigDecimal("200.00"));
        assertThrows(SaldoInsuficienteException.class, () -> medico.descontarSaldo(new BigDecimal("300.00")));
    }
}
