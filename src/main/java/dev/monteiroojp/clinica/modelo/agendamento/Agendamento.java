package dev.monteiroojp.clinica.modelo.agendamento;


import dev.monteiroojp.clinica.modelo.servico.Servico;
import dev.monteiroojp.clinica.modelo.usuario.Gestor;
import dev.monteiroojp.clinica.modelo.usuario.Medico;
import dev.monteiroojp.clinica.modelo.usuario.Paciente;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

//id, paciente, medico, servico, dataHora, dataCriacao, status, valorTotal, feedback, aprovadoPor, dataAprovacao.
public class Agendamento {

    private Long id;
    private Paciente paciente;
    private Medico medico;
    private Servico servico;
    private LocalDateTime dataHora;
    private LocalDateTime dataCriacao;
    private StatusAgendamento status;
    private BigDecimal valorTotal;
    private String feedback;
    private Gestor aprovadoPor;
    private LocalDateTime dataAprovacao;

    public Agendamento(Long id, Paciente paciente, Medico medico, Servico servico, LocalDateTime dataHora){

        if(paciente == null)
            throw  new IllegalArgumentException("Agendamento não pode ter paciente vazio");

        if(medico == null)
            throw new IllegalArgumentException("Agendamento não pode ter médico vazio");

        if(servico == null)
            throw new IllegalArgumentException("Agendamento não pode ter serviço vazio");

        if(dataHora == null)
            throw new IllegalArgumentException("Agendamento não pode ter data-hora vazia");

        if(!dataHora.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Agendamento deve ter data-hora no futuro");

        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.servico = servico;
        this.dataHora = dataHora;
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusAgendamento.PENDENTE;
        this.valorTotal = paciente.getPlano().calcularValorComDesconto(servico.getValor());
        this.feedback = "";
        this.aprovadoPor = null;
        this.dataAprovacao = null;
    }

    public Long getId() {
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if(paciente == null)
            throw  new IllegalArgumentException("Agendamento não pode ter paciente vazio");

        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if(medico == null)
            throw new IllegalArgumentException("Agendamento não pode ter médico vazio");

        this.medico = medico;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        if(servico == null)
            throw new IllegalArgumentException("Agendamento não pode ter serviço vazio");

        this.servico = servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        if(dataHora == null)
            throw new IllegalArgumentException("Agendamento não pode ter data-hora vazia");

        if(!dataHora.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Agendamento deve ter data-hora no futuro");

        this.dataHora = dataHora;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public String getFeedback() {
        return feedback;
    }

    //Métodos que manipulam o status do agendamento
    public void aprovar(Gestor gestor){
        if(status != StatusAgendamento.PENDENTE)
            throw new IllegalArgumentException("Só pode aprovar agendamentos que estão pendentes");

        if(gestor == null)
            throw new IllegalArgumentException("Gestor não pode ser nulo");

        this.status = StatusAgendamento.CONFIRMADO;
        this.aprovadoPor = gestor;
        this.dataAprovacao = LocalDateTime.now();
    }

    public void cancelar(){
        if(status == StatusAgendamento.CONCLUIDO)
            throw new IllegalArgumentException("Não se pode cancelar um agendamento já concluido");

        if(status == StatusAgendamento.CANCELADO)
            throw new IllegalArgumentException("Não se pode cancelar um agendamento já cancelado");

        this.status = StatusAgendamento.CANCELADO;
    }

    public void concluir(){
        if(status != StatusAgendamento.CONFIRMADO)
            throw new IllegalArgumentException("Só pode concluir um agendamento que apresentava o status 'confirmado' anteriormente");

        this.status = StatusAgendamento.CONCLUIDO;
    }

    public void setFeedback(String feedback) {
        if(status != StatusAgendamento.CONCLUIDO)
            throw new IllegalArgumentException("Só pode ser adicionado feedback se o agendamento estiver status 'concluido'");

        if(feedback == null || feedback.isBlank())
            throw new IllegalArgumentException("Feedback não pode ser vazio");

        this.feedback = feedback.trim();
    }

    public Gestor getAprovadoPor() {
        return aprovadoPor;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public BigDecimal getValorMedico(){
        return getValorTotal().multiply(new BigDecimal("0.6")).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getValorClinica(){
        return getValorTotal().multiply(new BigDecimal("0.4")).setScale(2, RoundingMode.HALF_UP);
    }


    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Agendamento aux = (Agendamento) obj;

        return Objects.equals(id, aux.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "paciente=" + paciente.getName() +
                ", medico=" + medico.getName() +
                ", servico=" + servico.getName() +
                ", dataHora=" + dataHora +
                ", status=" + status +
                ", valorTotal=" + valorTotal +
                "}";
    }
}
