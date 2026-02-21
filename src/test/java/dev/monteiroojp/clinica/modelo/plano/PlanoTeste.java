package dev.monteiroojp.clinica.modelo.plano;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class PlanoTeste {

    private Plano plano;

    @BeforeEach
    void setup(){
        plano = new Plano(1L, "  UNIMED  ", new BigDecimal("0.2"), true);
    }

    //Instancia correta
    @Test
    @DisplayName("Classe Plano deve armazenar nome já normalizado")
    void deveArmazenarNomeNormalizado(){
        assertEquals("UNIMED",  plano.getNome());
    }

    @Test
    @DisplayName("Classe Plano não deve formatar nome já normalizado na instanciação")
    void naoDeveFormatarNome(){
        Plano plano1 = new Plano(1L, "UNIMED", new BigDecimal("0.2"), true);
        assertEquals("UNIMED", plano1.getNome());
    }

    //Testes dos métodos relacionados a cálculo de desconto
    @Test
    @DisplayName("Classe Plano deve ser capaz de calcular corretamente o desconto em cima de um determinado valor")
    void deveCalcularDescontoCorretamente(){
        assertEquals(new BigDecimal("40.00"), plano.calcularDesconto(new BigDecimal("200.00")));
    }

    @Test
    @DisplayName("Classe Plano deve ser capaz de calcular corretamente o valor total após aplicar o desconto")
    void deveCalcularValorFinalCorretamente(){
        assertEquals(new BigDecimal("160.00"), plano.calcularValorComDesconto(new BigDecimal("200.00")));
    }

    //Teste das exceptions
    @Test
    @DisplayName("Classe Plano deve rejeitar nome vazio no construtor/set")
    void deveRejeitarNomeVazio(){
        assertThrows(IllegalArgumentException.class, () -> new Plano(1L, null, new BigDecimal("0.2"), true));
        assertThrows(IllegalArgumentException.class, () -> new Plano(1L, "     ", new BigDecimal("0.2"), true));

        assertThrows(IllegalArgumentException.class, () -> plano.setNome(null));
        assertThrows(IllegalArgumentException.class, () -> plano.setNome("     "));
    }

    @Test
    @DisplayName("Classe Plano deve rejeitar percentual de desconto null")
    void deveRejeitarDescontoNull(){
        assertThrows(IllegalArgumentException.class, () -> new Plano(1L, "UNIMED", null, true));
        assertThrows(IllegalArgumentException.class, () -> plano.setPercentualDesconto(null));
    }

    @Test
    @DisplayName("Classe plano só deve aceitar percentual de desconto entre 0 e 1")
    void deveRejeitarDescontoForaDoIntervalo(){
        //Intervalo (..., 0]
        assertThrows(IllegalArgumentException.class, () -> new Plano(1L, "UNIMED", new BigDecimal("0.00"), true));
        assertThrows(IllegalArgumentException.class, () -> new Plano(1L, "UNIMED", new BigDecimal("-0.01"), true));
        assertThrows(IllegalArgumentException.class, () -> plano.setPercentualDesconto(new BigDecimal("0.00")));
        assertThrows(IllegalArgumentException.class, () -> plano.setPercentualDesconto(new BigDecimal("-0.01")));

        //Intervalo [1, ...)
        assertThrows(IllegalArgumentException.class, () -> new Plano(1L, "UNIMED", new BigDecimal("1.00"), true));
        assertThrows(IllegalArgumentException.class, () -> new Plano(1L, "UNIMED", new BigDecimal("1.01"), true));
        assertThrows(IllegalArgumentException.class, () -> plano.setPercentualDesconto(new BigDecimal("1.00")));
        assertThrows(IllegalArgumentException.class, () -> plano.setPercentualDesconto(new BigDecimal("1.01")));
    }

    //Teste equals
    @Test
    @DisplayName("Classe Plano deve reconhecer duas instancias diferentes, com mesmo id, como iguais")
    void deveIdentificarPlanosIguais(){
        Plano plano1 = new Plano(1L, "UNIMED", new BigDecimal("0.2"), true);
        Plano plano2 = new Plano(1L, "UNIMED", new BigDecimal("0.2"), true);
        assertEquals(plano1, plano2);
    }
}
