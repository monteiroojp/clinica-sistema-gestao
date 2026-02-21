package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public final class Gestor extends Funcionario{

    private SetorGestor setor;

    public Gestor(String name, Cpf cpf, Email email, Telefone telefone, LocalDate dataNascimento, char sexo, String senha, BigDecimal salarioBase, LocalDate dataAdmissao, SetorGestor setor){
        super(name, cpf, email, telefone, dataNascimento, sexo, senha, salarioBase, dataAdmissao);

        if(setor == null)
            throw new IllegalArgumentException("Setor não pode ser vazio");

        this.setor = setor;
    }

    public SetorGestor getSetor() {
        return setor;
    }

    public void setSetor(SetorGestor setor) {
        if(setor == null)
            throw new IllegalArgumentException("Setor não pode ser vazio");

        this.setor = setor;
    }

    public boolean isResponsavelPor(SetorGestor setor){
        return this.setor == setor;
    }

    @Override
    public BigDecimal calcularSalarioTotal(){
        BigDecimal taxaAumentoAnual = new BigDecimal("1.05");
        BigDecimal aumentoSalarial = taxaAumentoAnual.pow(this.calcularTempoServicoEmAnos());

        return salarioBase.multiply(aumentoSalarial).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Gestor{" + super.toString() +
                ", setor=" + setor +
                "}";
    }
}
