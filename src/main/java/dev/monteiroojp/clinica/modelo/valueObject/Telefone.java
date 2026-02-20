package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.TelefoneInvalidoException;
import java.util.Objects;

public final class Telefone {

    private final String value;

    public Telefone(String value){
        if(value == null)
            throw new TelefoneInvalidoException("Telefone não pode ser nulo");

        String normalizado = value.replaceAll("[^0-9]", "");

        if(!normalizado.matches("^\\d{10,11}$"))
            throw new TelefoneInvalidoException("Formato de telefone inválido");

        this.value = normalizado;
    }

    public String getValue(){
        return value;
    }

    public String getValueFormatado(){
        if(value.length() == 10){
            return String.format("(%s) %s-%s",
                    value.substring(0, 2),
                    value.substring(2, 6),
                    value.substring(6, 10));
        }
        else {
            return String.format("(%s) %s-%s",
                    value.substring(0, 2),
                    value.substring(2, 7),
                    value.substring(7, 11));
        }
    }

    //Override da classe object
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString(){
        return this.getValueFormatado();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Telefone aux = (Telefone) obj;

        return this.value.equals(aux.value);
    }
}
