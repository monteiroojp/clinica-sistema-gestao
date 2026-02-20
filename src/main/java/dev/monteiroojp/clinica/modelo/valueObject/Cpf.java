package dev.monteiroojp.clinica.modelo.valueObject;

import dev.monteiroojp.clinica.excecao.CpfInvalidoException;

import java.util.Objects;

public final class Cpf {

    private final String value;

    public Cpf(String value){
        if(value == null)
            throw new CpfInvalidoException("CPF não pode ser nulo");

        if(!validate(value))
            throw new CpfInvalidoException("Formato de CPF inválido ou todos dígitos iguais");

        value = normalize(value);
        this.value = value;
    }

    private boolean validate(String cpf){
        //Valida cpf com todos digitos iguais
        char aux = cpf.charAt(0);
        boolean allEqual = true;
        for(int i=1; i<cpf.length(); i++){
            if(cpf.charAt(i) != aux && cpf.charAt(i) != '.' && cpf.charAt(i) != '-') {
                allEqual = false;
                break;
            }
        }

        String regex1 = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
        String regex2 = "^\\d{11}$";

        return (cpf.matches(regex1) || cpf.matches(regex2)) && !allEqual;
    }

    private String normalize(String cpf){
        StringBuilder value = new StringBuilder();
        for(int i=0; i<cpf.length(); i++){
            if(cpf.charAt(i) != '.' && cpf.charAt(i) != '-')
                value.append(cpf.charAt(i));
        }

        return value.toString();
    }

    public String getValue(){
        return value;
    }

    public String getValueFormatado() {
        return String.format("%s.%s.%s-%s",
                value.substring(0, 3),
                value.substring(3, 6),
                value.substring(6, 9),
                value.substring(9, 11)
        );
    }

    //Overrides da classe Object
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

        Cpf aux = (Cpf) obj;

        return this.value.equals(aux.value);
    }

}
