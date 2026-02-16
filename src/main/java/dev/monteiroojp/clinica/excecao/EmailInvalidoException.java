package dev.monteiroojp.clinica.excecao;

public final class EmailInvalidoException extends RuntimeException{
    public EmailInvalidoException(String msg){
        super(msg);
    }
}
