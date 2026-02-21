package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.CpfInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CpfTeste {

    //Testes de instância correta
    @Test
    @DisplayName("Classe CPF deve armazenar o value já normalizado a partir de um CPF nao normalizado")
    void deveArmazenarCpfNormalizado(){
        Cpf cpf = new Cpf("123.456.789-00");
        assertEquals("12345678900", cpf.getValue());
    }

    @Test
    @DisplayName("Classe CPF não deve formatar um cpf de entrada já normalizado")
    void deveNaoFormatarCpf(){
        Cpf cpf = new Cpf("12345678900");
        assertEquals("12345678900", cpf.getValue());
    }

    //Testes de exceptions
    @Test
    @DisplayName("Classe nao deve permitir instância Cpf é nulo")
    void deveRejeitarCpfNulo(){
        assertThrows(CpfInvalidoException.class, () -> new Cpf(null));
    }

    @Test
    @DisplayName("Classe nao deve permitir instância de Cpf com todos dígitos iguais")
    void deveRejeitarCpfComDigitosIguais(){
        assertThrows(CpfInvalidoException.class, () -> new Cpf("111.111.111-11"));
    }

    //Teste de get formatado
    @Test
    @DisplayName("Classe deve fornecer um Cpf formatado a partir do normalizado armazenado")
    void deveFornecerCpfFormatado(){
        Cpf cpf = new Cpf("12345678900");
        assertEquals("123.456.789-00", cpf.getValueFormatado());
    }

    //Teste do equals
    @Test
    @DisplayName("Instancias diferentes de Cpf devem ser iguais caso apresentem mesmo value")
    void cpfsComMesmoValueDevemSerIguais(){
        Cpf cpf1 = new Cpf("123.456.789-00");
        Cpf cpf2 = new Cpf("12345678900");
        assertEquals(cpf1, cpf2);
    }
}
