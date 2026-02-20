package dev.monteiroojp.clinica.modelo.usuario;

import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.modelo.valueObject.Email;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public abstract class Usuario {

    protected String name;
    protected Cpf cpf;
    protected Email email;
    protected Telefone telefone;
    protected LocalDate dataNascimento;
    protected char sexo;
    protected String senha;
    protected LocalDateTime dataCriacao;

    public Usuario(String name, Cpf cpf, Email email, Telefone telefone, LocalDate dataNascimento, char sexo, String senha) {
        //Validações
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");

        if(cpf == null)
            throw new IllegalArgumentException("CPF não pode ser vazio");

        if(email == null)
            throw new IllegalArgumentException("Email não pode ser vazio");

        if(telefone == null)
            throw new IllegalArgumentException("Telefone não pode ser vazio");

        if(dataNascimento == null)
            throw new IllegalArgumentException("Data de nascimento não pode ser vazio");

        if(dataNascimento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento não pode ser no futuro");

        if(sexo != 'm' && sexo != 'M' && sexo != 'f' && sexo != 'F')
            throw new IllegalArgumentException("Sexo inválido");

        if(senha == null || senha.isBlank())
            throw new IllegalArgumentException("Senha não pode ser vazia");

        //Atribuições
        this.name = name.trim();
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.sexo = Character.toUpperCase(sexo);
        this.senha = senha;
        this.dataCriacao = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");

        this.name = name.trim();
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        if(email == null)
            throw new IllegalArgumentException("Email não pode ser vazio");

        this.email = email;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        if(telefone == null)
            throw new IllegalArgumentException("Telefone não pode ser vazio");

        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if(dataNascimento == null)
            throw new IllegalArgumentException("Data de nascimento não pode ser vazio");

        if(dataNascimento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento não pode ser no futuro");

        this.dataNascimento = dataNascimento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        if(sexo != 'm' && sexo != 'M' && sexo != 'f' && sexo != 'F')
            throw new IllegalArgumentException("Sexo inválido");

        this.sexo = Character.toUpperCase(sexo);
    }

    public void setSenha(String senha) {
        if(senha == null || senha.isBlank())
            throw new IllegalArgumentException("Senha não pode ser vazia");

        this.senha = senha;
    }

    public LocalDateTime getDataCriacao(){
        return dataCriacao;
    }

    public int calcularIdade(){
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    //Override da classe object
    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Usuario aux = (Usuario) obj;
        return this.cpf.equals(aux.cpf);
    }

    @Override
    public int hashCode(){
        return this.cpf.hashCode();
    }

    @Override
    public String toString() {
        return "name='" + name + "'" +
                ", cpf=" + cpf.getValue() +
                ", email=" + email.getValue();
    }
}
