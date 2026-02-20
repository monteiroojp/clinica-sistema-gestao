package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.CrmInvalidoException;
import java.util.Objects;

public final class Crm {

    private final String value;

    public Crm(String value){
        if(value == null)
            throw new CrmInvalidoException("Crm não pode ser nulo");

        if(!value.matches("^CRM/[A-Z]{2}\\s\\d{4,6}$")){
            throw new CrmInvalidoException("Formato de CRM inváldio");
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

        Crm aux = (Crm) obj;
        return this.value.equals(aux.value);
    }
}
