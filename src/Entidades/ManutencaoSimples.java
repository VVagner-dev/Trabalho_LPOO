package Entidades;

public class ManutencaoSimples extends Manutencao {


    public ManutencaoSimples(Integer id, Equipamento equipamento, String descricao, double valorBase, boolean concluido) {
        super(id, equipamento, descricao, valorBase, concluido);
    }

    @Override
    public double calcularCusto() {
        return getValorBase();
    }

    @Override
    public String toString() {
        return "Manutencao{" +
                "id=" + getId() +
                ", equipamento=" + getEquipamento() +
                ", descricao='" + getDescricao() + '\'' +
                ", valorBase=" + getValorBase() +
                ", concluido=" + isConcluido() +
                '}';
    }

}
