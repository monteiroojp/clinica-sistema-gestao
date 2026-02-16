package dev.monteiroojp.clinica.excecao;

public final class CpfInvalidoException extends RuntimeException{
    public CpfInvalidoException(String msg){
        super(msg);
    }
}
