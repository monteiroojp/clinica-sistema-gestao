package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class FuncionarioTeste {

    private static class FuncionarioStub extends Funcionario{
        FuncionarioStub(String name, Cpf cpf, Email email, Telefone telefone, LocalDate dataNascimento, char sexo, String senha, BigDecimal salarioBase, LocalDate dataAdmissao){
            super(name, cpf, email, telefone, dataNascimento, sexo, senha, salarioBase, dataAdmissao);
        }

        @Override
        public BigDecimal calcularSalarioTotal(){
            return salarioBase;
        }
    }

    private Funcionario funcionario;

    @BeforeEach
    void setup(){
        funcionario = new FuncionarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                new BigDecimal("2000.00"),
                LocalDate.parse("2026-01-01"));
    }

    //Testes dos gets de tempo de contribuição
    @Test
    @DisplayName("Tempos de contribuição pós admissão devem estar corretos")
    void deveCalcularTempoDeContribuicaoCorretamente(){
        int years = Period.between(funcionario.dataAdmissao, LocalDate.now()).getYears();
        int months = Period.between(funcionario.dataAdmissao, LocalDate.now()).getMonths();

        assertEquals(years, funcionario.calcularTempoServicoEmAnos());
        assertEquals(months, funcionario.calcularTempoServicoEmMeses());
    }

    //Testes das exceptions
    @Test
    @DisplayName("Classe Funcionario deve rejeitar salarioBase não positivo ou null no construtor/set")
    void deveRejeitarSalarioNaoPositivoOuNull(){
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioStub("João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                new BigDecimal("-2000.00"),
                LocalDate.parse("2026-01-01")));

        assertThrows(IllegalArgumentException.class, () -> new FuncionarioStub("João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                null,
                LocalDate.parse("2026-01-01")));

        assertThrows(IllegalArgumentException.class, () -> funcionario.setSalarioBase(new BigDecimal("-2000.00")));
        assertThrows(IllegalArgumentException.class, () -> funcionario.setSalarioBase(null));
    }

    @Test
    @DisplayName("Classe funcionario deve rejeitar data de admissão null no construtor")
    void deveRejeitarDataDeAdmissaoNull(){
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioStub("João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                new BigDecimal("2000.00"),
                null));
    }
}
