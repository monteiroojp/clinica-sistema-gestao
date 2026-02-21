package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.modelo.plano.Plano;
import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;

import java.time.LocalDate;

public class Paciente extends Usuario{

    private String observacoes;
    private Plano plano;

    public Paciente(String name, Cpf cpf, Email email, Telefone telefone, LocalDate dataNascimento, char sexo, String senha, String observacoes, Plano plano){
        super(name, cpf, email, telefone, dataNascimento, sexo, senha);

        if(plano == null)
            throw new IllegalArgumentException("Plano não pode ser vazio");

        this.observacoes = observacoes == null ? "" : observacoes.trim();
        this.plano = plano;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = (observacoes == null) ? "" : observacoes.trim();
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        if(plano == null)
            throw new IllegalArgumentException("Plano não pode ser vazio");

        this.plano = plano;
    }

    @Override
    public String toString() {
        return "Paciente{" + super.toString() +
                ", plano=" + plano.getNome() +
                ", observacoes='" + (observacoes.isBlank() ? "Nenhuma" : observacoes) + "'" +
                "}";
    }
}
