package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.EmailInvalidoException;

import java.util.Objects;

public final class Email {

    private final String value;

    public Email(String value){
        if(value == null)
            throw new EmailInvalidoException("Email não pode ser nulo");

        value = value.toLowerCase().trim();

        if(!value.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}(\\.[a-z]{2,})?$")){
            throw new EmailInvalidoException("Formato de email inválido");
        }

        this.value = value;
    }

    public String getValue(){
        return value;
    }

    //Overrides da classe object
    @Override
    public int hashCode(){
        return Objects.hash(value);
    }

    @Override
    public String toString(){
        return value;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Email aux = (Email) obj;
        return this.value.equals(aux.value);
    }
}