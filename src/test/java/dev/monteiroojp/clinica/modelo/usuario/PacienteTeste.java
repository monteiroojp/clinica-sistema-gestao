package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.modelo.plano.Plano;
import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PacienteTeste {

    private Paciente paciente;

    @BeforeEach
    void setup(){
        paciente = new Paciente(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                "  Diabético  ",
                new Plano(1L, "UNIMED", new BigDecimal("0.2"), true));
    }

    //Testes de instancia correta
    @Test
    @DisplayName("Classe Paciente deve armazenar observação já normalizada")
    void deveArmazenarObservacoesNormalizada(){
        assertEquals("Diabético", paciente.getObservacoes());
    }

    @Test
    @DisplayName("Classe Paciente não deve formatar uma observação já normalizada na instanciação")
    void naoDeveFormatarObservacoesJaNormalizada(){
        Paciente paciente1 = new Paciente(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                "Diabético",
                new Plano(1L, "UNIMED", new BigDecimal("0.2"), true));

        assertEquals("Diabético", paciente1.getObservacoes());
    }

    @Test
    @DisplayName("Classe Paciente deve ter observações como uma string vazia caso seja passo null na instanciação/set")
    void deveArmazenarStringVaziaCasoNull(){
        Paciente paciente1 = new Paciente(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                null,
                new Plano(1L, "UNIMED", new BigDecimal("0.2"), true));

        assertEquals("", paciente1.getObservacoes());

        paciente.setObservacoes(null);
        assertEquals("", paciente.getObservacoes());
    }

    //Teste de exception
    @Test
    @DisplayName("Classe Pacietne deve rejeitar plano null no construtor/set")
    void deveRejeitarPlanoNull(){
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                "  Diabético  ",
                null));

        assertThrows(IllegalArgumentException.class, () -> paciente.setPlano(null));
    }
}
