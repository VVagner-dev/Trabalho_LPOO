package Entidades;

public class ManutencaoUrgente extends Manutencao {

    private double taxaUrgencia;

    public ManutencaoUrgente(Integer id, Equipamento equipamento, String descricao, double valorBase, boolean concluido, double taxaUrgencia) {
        super(id, equipamento, descricao, valorBase, concluido);
        this.taxaUrgencia = taxaUrgencia;
    }

    public double getTaxaUrgencia() {
        return taxaUrgencia;
    }

    public void setTaxaUrgencia(double taxaUrgencia) {
        this.taxaUrgencia = taxaUrgencia;
    }

    @Override
    public double calcularCusto() {
        return getValorBase()+(getValorBase()*(taxaUrgencia/100));
    }

    @Override
    public String toString() {
        return "Manutencao{" +
                "id=" + getId() +
                ", equipamento=" + getEquipamento() +
                ", descricao='" + getDescricao() + '\'' +
                ", Taxa Urgencia='" + taxaUrgencia +
                ", valorBase=" + getValorBase() +
                ", concluido=" + isConcluido() +
                '}';
    }
}
