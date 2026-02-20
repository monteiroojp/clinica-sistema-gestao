package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.EmailInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    //Testes de instância correta
    @Test
    @DisplayName("Email deve armazenar um formato válido já normalizado")
    void deveArmazenarNormalizado(){
        Email email = new Email("  emailTeste@teste.com    ");
        assertEquals("emailteste@teste.com", email.getValue());
    }

    @Test
    @DisplayName("Email nao deve formatar um email já normalizado na instância")
    void naoDeveFormatarEmailJaNormalizado(){
        Email email = new Email("emailteste@teste.com");
        assertEquals("emailteste@teste.com", email.getValue());
    }

    //Testes das exceptions
    @Test
    @DisplayName("Email nao deve aceitar null como parametro de instancia")
    void deveRejeitarNull(){
        assertThrows(EmailInvalidoException.class, () -> new Email(null));
    }

    @Test
    @DisplayName("Email nao deve aceitar dados com formatos invalidos")
    void deveRejeitarFormatosInvalidos(){
        assertThrows(EmailInvalidoException.class, () -> new Email("emailTeste.com.br"));
    }

    //Teste do equals
    @Test
    @DisplayName("Duas instancias diferentes de Email devem ser iguais desde que tenham mesmo value")
    void deveIdentificarEmailsComMesmoValue(){
        Email email1 = new Email("emailteste@teste.com");
        Email email2 = new Email("emailteste@teste.com");
        assertEquals(email1, email2);
    }

}
