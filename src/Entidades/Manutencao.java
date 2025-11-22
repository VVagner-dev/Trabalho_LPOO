package Entidades;

import java.time.LocalDate;


public abstract class Manutencao {

    private Integer id;
    private Equipamento equipamento;
    private String descricao;
    private double valorBase;
    private boolean concluido;

    public Manutencao(Integer id, Equipamento equipamento, String descricao, double valorBase, boolean concluido) {
        this.id = id;
        this.equipamento = equipamento;
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.concluido = concluido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public abstract double calcularCusto();

    @Override
    public String toString() {
        return "Manutencao{" +
                "id=" + id +
                ", equipamento=" + equipamento +
                ", descricao='" + descricao + '\'' +
                ", valorBase=" + valorBase +
                ", concluido=" + concluido +
                '}';
    }
}
