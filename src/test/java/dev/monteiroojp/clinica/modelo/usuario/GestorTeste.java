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

public class GestorTeste {

    private Gestor gestor;

    @BeforeEach
    void setup(){
        gestor = new Gestor(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                new BigDecimal("2000.00"),
                LocalDate.parse("2023-01-01"),
                SetorGestor.FINANCEIRO);
    }

    //Teste de instancia correta
    @Test
    @DisplayName("Classe Gestor deve armazenar corretamente o Enum referente ao seu setor")
    void deveArmazenarEnumCorretamente(){
        assertEquals(SetorGestor.FINANCEIRO, gestor.getSetor());
    }

    //Teste do get no salario total
    @Test
    @DisplayName("Classe Gestor deve calcular corretamente o salario total, crescimento anual de 5%")
    void deveCalcularSalarioTotalCorretamente(){
        assertEquals(new BigDecimal("2315.25"), gestor.calcularSalarioTotal());
    }

    //Testes de responsabilidade
    @Test
    @DisplayName("Classe Gestor deve assegurar a responsabilidade ao setor que a instância está associada")
    void deveReconhecerResponsabilidadeEmMatchDeSetor(){
        assertTrue(gestor.isResponsavelPor(SetorGestor.FINANCEIRO));
    }

    @Test
    @DisplayName("Classe Gestor deve negar responsabilidade quando o setor não está associado a instância em específico")
    void deveRejeitarResponsabilidadeEmSetoresDiferentes(){
        assertFalse(gestor.isResponsavelPor(SetorGestor.RH));
    }

    //Teste da exception
    @Test
    @DisplayName("Classe Gestor deve rejeitar setor null no consrtutor/set")
    void deveRejeitarSetorNull(){
        assertThrows(IllegalArgumentException.class, () ->  new Gestor(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123",
                new BigDecimal("2000.00"),
                LocalDate.parse("2026-01-01"),
                null));

        assertThrows(IllegalArgumentException.class, () -> gestor.setSetor(null));
    }


}
