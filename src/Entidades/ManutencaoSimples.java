package Entidades;

// Filho da classe abstrata Manutencao
public class ManutencaoSimples extends Manutencao {

    public ManutencaoSimples(Integer id, Equipamento equipamento, String descricao, double valorBase, boolean concluido) {
        super(id, equipamento, descricao, valorBase, concluido);
    }

    //polimorfismo da função calculoCusto
    @Override
    public double calcularCusto() {
        return getValorBase();
    } //retorna o valor

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
