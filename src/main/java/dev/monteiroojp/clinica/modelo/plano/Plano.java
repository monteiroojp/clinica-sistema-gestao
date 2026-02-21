package dev.monteiroojp.clinica.modelo.plano;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Plano {
    private Long id;
    private String nome;
    private BigDecimal percentualDesconto;
    private Boolean ativo;

    public Plano(Long id, String nome, BigDecimal percentualDesconto, Boolean ativo){
        if(nome == null || nome.isBlank()){
            throw  new IllegalArgumentException("Nome não pode ser vazio");
        }

        if(percentualDesconto == null || percentualDesconto.compareTo(BigDecimal.ZERO) <= 0 || percentualDesconto.compareTo(BigDecimal.ONE) >= 0)
            throw new IllegalArgumentException("Desconto deve ser um valor entre 0 e 1");

        this.id = id;
        this.nome = nome.trim();
        this.percentualDesconto = percentualDesconto;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw  new IllegalArgumentException("Nome não pode ser vazio");
        }

        this.nome = nome.trim();
    }

    public BigDecimal getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(BigDecimal percentualDesconto) {
        if(percentualDesconto == null || percentualDesconto.compareTo(BigDecimal.ZERO) <= 0 || percentualDesconto.compareTo(BigDecimal.ONE) >= 0)
            throw new IllegalArgumentException("Desconto deve ser um valor entre 0 e 1");

        this.percentualDesconto = percentualDesconto;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal calcularDesconto(BigDecimal valorTotal){
        return valorTotal.multiply(percentualDesconto).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularValorComDesconto(BigDecimal valorTotal){
        return valorTotal.subtract(calcularDesconto(valorTotal));
    }

    //Overrides de métodos da classe object
    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Plano aux = (Plano) obj;

        return id.equals(aux.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Plano{" +
                "nome='" + nome + "'" +
                ", desconto=" + percentualDesconto.multiply(new BigDecimal("100")) + "%" +
                ", ativo=" + ativo +
                "}";
    }
}
