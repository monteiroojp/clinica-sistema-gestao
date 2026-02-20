package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class UsuarioTest {

    private static class UsuarioStub extends Usuario{
        UsuarioStub(String name, Cpf cpf, Email email, Telefone telefone, LocalDate dataNascimento, char sexo, String senha){
            super(name, cpf, email, telefone, dataNascimento, sexo, senha);
        }
    }

    private Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = new UsuarioStub(
                "   João Pedro    ",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123");
    }

    //Testes de instancia correta
    @Test
    @DisplayName("Classe Usuario deve armazenar o nome já normalizado")
    void deveArmazenarNomeNormalizado(){
        assertEquals("João Pedro", usuario.getName());
    }

    @Test
    @DisplayName("Classe Usuario deve armazenar o sexo já normalizado")
    void deveArmazenarSexoNormalizado(){
        assertEquals('M', usuario.getSexo());
    }

    @Test
    @DisplayName("Classe usuario armazena data de criação corretamente")
    void deveArmazenarDataDeCriacaoCorretamente(){
        assertEquals(LocalDateTime.now(), usuario.getDataCriacao());
    }

    //Teste de calculo de idade
    @Test
    @DisplayName("Classe Usuario deve conseguir fornecer a idade das suas instancias de forma correta")
    void deveCalcularIdadeCorretamente(){
        int correctAge = Period.between(usuario.dataNascimento, LocalDate.now()).getYears();
        assertEquals(correctAge, usuario.calcularIdade());
    }

    //Teste das exceptions
    @Test
    @DisplayName("Classe Usuario deve rejeitar nomes null ou blanks no consrtutor/set")
    void deveRejeitarNomeNullOuBlank(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                null,
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123"));

        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "        ",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123"));

        assertThrows(IllegalArgumentException.class, () -> usuario.setName(null));
        assertThrows(IllegalArgumentException.class, () -> usuario.setName("     "));
    }

    @Test
    @DisplayName("Classe Usuario nao deve rejeitar cpf null no construtor")
    void deveRejeitarCpfNull(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                null,
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123"));
    }

    @Test
    @DisplayName("Classe Usuario nao deve rejeitar email null no construtor/set")
    void deveRejeitarEmailNull(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                null,
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123"));

        assertThrows(IllegalArgumentException.class, () -> usuario.setEmail(null));
    }

    @Test
    @DisplayName("Classe Usuario nao deve rejeitar telefone null no consrtutor/set")
    void deveRejeitarTelefoneNull(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                null,
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123"));

        assertThrows(IllegalArgumentException.class, () -> usuario.setTelefone(null));
    }

    @Test
    @DisplayName("Classe usuario deve rejeitar data de nascimento null no construtor/set")
    void deveRejeitarDataDeNascimentoNull(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                null,
                'm',
                "senha123"));

        assertThrows(IllegalArgumentException.class, () -> usuario.setDataNascimento(null));
    }

    @Test
    @DisplayName("Classe usuario deve rejeitar datas de nascimento no futuro no consrtutor/set")
    void deveRejeitarDataDeNascimentoNoFuturo(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.now().plusDays(1),
                'm',
                "senha123"));

        assertThrows(IllegalArgumentException.class, () -> usuario.setDataNascimento(LocalDate.now().plusDays(1)));
    }

    @Test
    @DisplayName("Classe usuario deve rejeitar sexos que não sejam M/m ou F/f")
    void deveRejeitarSexoInvalido(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'l',
                "senha123"));

        assertThrows(IllegalArgumentException.class, () -> usuario.setSexo('l'));
    }

    @Test
    @DisplayName("Classe usuario deve rejeitar senhas null ou blank no consrtutor/set")
    void deveRejeitarSenhaNullOuBlank(){
        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                null));

        assertThrows(IllegalArgumentException.class, () -> new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "          "));

        assertThrows(IllegalArgumentException.class, () -> usuario.setSenha(null));
        assertThrows(IllegalArgumentException.class, () -> usuario.setSenha("        "));
    }

    //Teste do equals
    @Test
    @DisplayName("Classe usuario deve ter duas instâncias diferentes, com mesmo value para cpf, como iguais")
    void deveIdentificarUsuariosComCpfIgual(){
        Usuario user1 = new UsuarioStub(
                "João Pedro",
                new Cpf("123.456.789-00"),
                new Email("emailteste@teste.com"),
                new Telefone("(12) 34567-8900"),
                LocalDate.parse("2000-01-01"),
                'm',
                "senha123");

        Usuario user2 = new UsuarioStub(
                "Fernanda Silva",
                new Cpf("123.456.789-00"),
                new Email("emailteste2@teste.com"),
                new Telefone("(12) 34567-8911"),
                LocalDate.parse("2000-01-02"),
                'F',
                "senha1234");

        assertEquals(user1, user2);
    }
}
