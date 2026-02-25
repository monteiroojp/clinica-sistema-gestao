package dev.monteiroojp.clinica.modelo.servico;


import dev.monteiroojp.clinica.modelo.usuario.Medico;

import java.math.BigDecimal;
import java.util.Objects;

//id, nome, valor, duracao, ocupacaoNecessaria
public class Servico {

    private Long id;
    private String name;
    private BigDecimal valor;
    private Integer duracaoMinutos;
    private String ocupacaoNecessaria;

    public Servico(Long id, String name, BigDecimal valor, int duracaoMinutos, String ocupacaoNecessaria){

        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");

        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw  new IllegalArgumentException("Valor do serviço deve ser positivo");

        if(duracaoMinutos <= 0)
            throw  new IllegalArgumentException("Duração, em minutos, do serviço deve ser positiva");

        this.id = id;
        this.name = name.trim();
        this.valor = valor;
        this.duracaoMinutos = duracaoMinutos;
        this.ocupacaoNecessaria = ocupacaoNecessaria == null ? "" : ocupacaoNecessaria.trim().toLowerCase();
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("ID já foi definido");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");

        this.name = name.trim();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw  new IllegalArgumentException("Valor do serviço deve ser positivo");

        this.valor = valor;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        if(duracaoMinutos == null || duracaoMinutos <= 0)
            throw  new IllegalArgumentException("Duração, em minutos, do serviço deve ser positiva");

        this.duracaoMinutos = duracaoMinutos;
    }

    public String getOcupacaoNecessaria() {
        return ocupacaoNecessaria;
    }

    public void setOcupacaoNecessaria(String ocupacaoNecessaria) {
        this.ocupacaoNecessaria = ocupacaoNecessaria == null ? "" : ocupacaoNecessaria.trim().toLowerCase();
    }

    public boolean podeSerRealizadoPor(Medico medico){
        if(ocupacaoNecessaria == null || ocupacaoNecessaria.isBlank())
            return true;

        return ocupacaoNecessaria.equals(medico.getOcupacao());
    }

    //Override dos métodos da object
    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Servico aux = (Servico) obj;

        return Objects.equals(this.id, aux.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Servico{" +
                "nome='" + name + "'" +
                ", valorBase=" + valor +
                ", duracaoMinutos=" + duracaoMinutos +
                ", ocupacaoRequerida='" + (ocupacaoNecessaria.isBlank() ? "Qualquer" : ocupacaoNecessaria) + "'" +
                "}";
    }
}
