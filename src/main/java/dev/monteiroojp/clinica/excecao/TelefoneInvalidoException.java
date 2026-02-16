package dev.monteiroojp.clinica.excecao;

public final class TelefoneInvalidoException extends RuntimeException{
    public TelefoneInvalidoException(String msg){
        super(msg);
    }
}
