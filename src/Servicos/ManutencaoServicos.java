package Servicos;

import Entidades.*;

import java.util.ArrayList;

public class ManutencaoServicos {

    ArrayList<Manutencao> manutencaos;
    EquipamentoServicos es;

    public ManutencaoServicos(EquipamentoServicos es) {
        this.manutencaos = new ArrayList<>();
        this.es = es;
    }

    private void validarId(Integer id){
        for (Manutencao manutencao : manutencaos){
            if(manutencao.getId().equals(id)){
                throw new RuntimeException("ID já cadastrado");
            }
        }
    }

    public void cadastrarSimples(Integer id, Integer idEquipamento, String descricao, Double valorBase, boolean concluido){
        Equipamento equipamento = es.buscarPorId(idEquipamento);
        validarId(id);
        Manutencao e = new ManutencaoSimples(id,equipamento,descricao,valorBase,concluido);
        manutencaos.add(e);
    }

    public void cadastrarRecorrente(Integer id,Integer idEquipamento, String descricao, Double valorBase, boolean concluido, double desconto){
        Equipamento equipamento = es.buscarPorId(idEquipamento);
        validarId(id);
        Manutencao e = new ManutencaoRecorrente(id,equipamento,descricao,valorBase,concluido, desconto);
        manutencaos.add(e);
    }

    public void cadastrarUrgente(Integer id,Integer idEquipamento, String descricao, Double valorBase, boolean concluido, double taxa){
        Equipamento equipamento = es.buscarPorId(idEquipamento);
        validarId(id);
        Manutencao e = new ManutencaoUrgente(id,equipamento,descricao,valorBase,concluido, taxa);
        manutencaos.add(e);
    }

    public ArrayList<Manutencao> listar(){
        return manutencaos;
    }

    public ArrayList<Manutencao> listarPendentes(){
        ArrayList<Manutencao> pendentes = new ArrayList<>();
        for (Manutencao manutencao : manutencaos){
            if(manutencao.isConcluido() == false){
                pendentes.add(manutencao);
            }
        }
        return pendentes;
    }
    public Manutencao buscarPorId(Integer id){
        for (Manutencao manutencao : manutencaos){
            if(manutencao.getId().equals(id)){
                return manutencao;
            }
        }
        return null;
    }

    public void concluirManutencao(Integer id){
        Manutencao manutencao = buscarPorId(id);
        if(manutencao == null){
            throw new RuntimeException("ID não encontrado");
        }else buscarPorId(id).setConcluido(true);
    }

    public void removerPorId(Integer id){
        Manutencao manutencao = buscarPorId(id);
        if(manutencao == null){
            throw new RuntimeException("ID não encontrado");
        }else manutencaos.remove(manutencao);
    }



}
