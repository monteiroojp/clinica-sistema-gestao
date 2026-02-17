package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.excecao.SaldoInsuficienteException;
import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Crm;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Medico extends Usuario{

    private Crm crm;
    private String ocupacao;
    private BigDecimal saldo;

    public Medico(String name, Cpf cpf, Email email, Telefone telefone, LocalDate dataNascimento, char sexo, String senha, Crm crm, String ocupacao){
        super(name, cpf, email, telefone, dataNascimento, sexo, senha);

        if(crm == null)
            throw new IllegalArgumentException("CRM não pode ser vazio");

        if(ocupacao == null || ocupacao.isBlank())
            throw new IllegalArgumentException("Ocupação não pode ser vazia");

        this.crm = crm;
        this.ocupacao = ocupacao.trim().toLowerCase();
        this.saldo = BigDecimal.ZERO;
    }

    public Crm getCrm() {
        return crm;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        if(ocupacao == null || ocupacao.isBlank())
            throw new IllegalArgumentException("Ocupação não pode ser vazia");

        this.ocupacao = ocupacao.trim().toLowerCase();
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void adicionarSaldo(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor deve ser positivo");

        saldo = saldo.add(valor);
    }

    public void descontarSaldo(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor deve ser positivo");

        if(saldo.compareTo(valor) < 0)
            throw new SaldoInsuficienteException("Saldo insuficiente");

        saldo = saldo.subtract(valor);
    }

    @Override
    public String toString() {
        return "Medico{" + super.toString() +
                ", crm=" + crm.getValue() +
                ", ocupacao='" + ocupacao + "'" +
                ", saldo=" + saldo +
                "}";
    }
}
