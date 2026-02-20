package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public abstract class Funcionario extends Usuario{

    protected BigDecimal salarioBase;
    protected LocalDate dataAdmissao;

    public Funcionario(String name, Cpf cpf, Email email, Telefone telefone, LocalDate dataNascimento, char sexo, String senha, BigDecimal salarioBase, LocalDate dataAdmissao){
        super(name, cpf, email, telefone, dataNascimento, sexo, senha);

        if(salarioBase == null || salarioBase.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Salario base tem que ser positivo e o objeto não nulo");

        if(dataAdmissao == null)
            throw new IllegalArgumentException("Data de admissão não pode ser vazia");

        if(dataAdmissao.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de admissão não pode ser no futuro");

        this.salarioBase = salarioBase;
        this.dataAdmissao = dataAdmissao;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        if(salarioBase == null || salarioBase.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Salario base tem que ser positivo e o objeto ser não nulo");

        this.salarioBase = salarioBase;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public int calcularTempoServicoEmAnos(){
        return Period.between(dataAdmissao, LocalDate.now()).getYears();
    }

    public int calcularTempoServicoEmMeses(){
        return Period.between(dataAdmissao, LocalDate.now()).getMonths();
    }

    public abstract BigDecimal calcularSalarioTotal();
}
