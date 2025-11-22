package Entidades;

public class ManutencaoRecorrente extends Manutencao {
    private double desconto;

    public ManutencaoRecorrente(Integer id, Equipamento equipamento, String descricao, double valorBase, boolean concluido, double desconto) {
        super(id, equipamento, descricao, valorBase, concluido);
        this.desconto = desconto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }


    @Override
    public double calcularCusto() {
        return getValorBase()-(getValorBase()*(desconto/100));
    }

    @Override
    public String toString() {
        return "Manutencao{" +
                "id=" + getId() +
                ", equipamento=" + getEquipamento() +
                ", descricao='" + getDescricao() + '\'' +
                ", desconto='" + desconto +
                ", valorBase=" + getValorBase() +
                ", concluido=" + isConcluido() +
                '}';
    }
}
