package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.CrmInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CrmTeste {

    //Teste de instância correta
    @Test
    @DisplayName("Classe Crm deve receber e armazenar, sem normalizar, um crm/cro que esteja no formato correto")
    void deveArmazenarSemNormalizar(){
        Crm crm = new Crm("CRM/MG 123456");
        assertEquals("CRM/MG 123456", crm.getValue());
    }

    //Testes das exceptions
    @Test
    @DisplayName("Classe crm nao deve aceitar null como parâmetro para instância")
    void deveRejeitarNull(){
        assertThrows(CrmInvalidoException.class, () -> new Crm(null));
    }

    @Test
    @DisplayName("Classe crm nao deve aceitar esse dado com formato invalido")
    void deveRejeitarCrmInvalidos(){
        assertThrows(CrmInvalidoException.class, () -> new Crm("CRO-SP 123456"));
    }

    //Teste do equals
    @Test
    @DisplayName("Classe crm deve reconhecer duas instancias como iguais desde que tenham mesmo value")
    void deveIdentificarCrmsIguais(){
        Crm crm1 = new Crm("CRM/MG 123456");
        Crm crm2 = new Crm("CRM/MG 123456");
        assertEquals(crm1, crm2);
    }
}
