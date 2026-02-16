package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.CrmInvalidoException;
import java.util.Objects;

public final class Crm {

    private final String Value;

    public Crm(String value){
        if(value == null)
            throw new CrmInvalidoException("Crm não pode ser nulo");

        if(!value.matches("^(CRO|CRM)/[A-Z]{2} \\d{4,6}$")){
            throw new CrmInvalidoException("Formato de CRM inváldio");
        }

        this.Value = value;
    }

    public String getValue(){
        return Value;
    }

    //Overrides da classe object
    @Override
    public int hashCode(){
        return Objects.hash(Value);
    }

    @Override
    public String toString(){
        return Value;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Crm aux = (Crm) obj;
        return this.Value.equals(aux.Value);
    }
}
