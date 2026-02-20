package dev.monteiroojp.clinica.modelo.valueObject;


import dev.monteiroojp.clinica.excecao.TelefoneInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TelefoneTest {

    //Testes de instancia correta
    @Test
    @DisplayName("Classe Telefone deve armazenar o value normalizado a partir de um telefone não normalizado ")
    void deveArmazenarNormalizado(){
        Telefone tel1 = new Telefone("(12) 34567-8900");
        Telefone tel2 = new Telefone("(12) 3456-7890");
        assertEquals("12345678900", tel1.getValue());
        assertEquals("1234567890", tel2.getValue());
    }

    @Test
    @DisplayName("Classe Telefone nao deve formatar um value já normalizado fornecido na instanciação")
    void naoDeveFormatarValorNormalizado(){
        Telefone tel1 = new Telefone("12345678900");
        Telefone tel2 = new Telefone("1234567890");
        assertEquals("12345678900", tel1.getValue());
        assertEquals("1234567890", tel2.getValue());
    }

    //Teste de exceptions
    @Test
    @DisplayName("Classe Telefone deve rejeitar instancia com null")
    void deveRejeitarTelefoneNull(){
        assertThrows(TelefoneInvalidoException.class, () -> new Telefone(null));
    }

    @Test
    @DisplayName("Classe telefone deve rejeitar formatos inválidos de telefone")
    void deveRejeitarFormatoInvalidoTelefone(){
        assertThrows(TelefoneInvalidoException.class, () -> new Telefone("123"));
        assertThrows(TelefoneInvalidoException.class, () -> new Telefone("123456789000"));
        assertThrows(TelefoneInvalidoException.class, () -> new Telefone("(12) 34567-89000"));
    }

    //Teste de get formatado
    @Test
    @DisplayName("Classe telefone deve ser capaz de fornecer o telefone formatado a partir do normalizado armazenado")
    void deveFornecerTelefoneFormatado(){
        Telefone tel1 = new Telefone("12345678900");
        Telefone tel2 = new Telefone("1234567890");
        assertEquals("(12) 34567-8900", tel1.getValueFormatado());
        assertEquals("(12) 3456-7890", tel2.getValueFormatado());
    }

    //Teste do equals
    @Test
    @DisplayName("Instancias diferentes da classe value devem ser iguais se tiverem mesmo value")
    void TelefoneComMesmoValueDevemSerIguais(){
        Telefone tel1 = new Telefone("12345678900");
        Telefone tel2 = new Telefone("(12) 34567-8900");
        Telefone tel3 = new Telefone("1234567890");
        Telefone tel4 = new Telefone("(12) 3456-7890");
        assertEquals(tel1, tel2);
        assertEquals(tel3, tel4);
    }
}
