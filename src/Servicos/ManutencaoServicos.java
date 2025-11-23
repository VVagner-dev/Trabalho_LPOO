package Servicos;

import Entidades.*;
import Repositorios.ManutencaoRepositorioArquivo;

import java.util.ArrayList;

public class ManutencaoServicos {

    private ArrayList<Manutencao> manutencoes;
    private EquipamentoServicos es;
    private ManutencaoRepositorioArquivo repositorio;

    public ManutencaoServicos(EquipamentoServicos es) {
        this.manutencoes = new ArrayList<>();
        this.es = es;
        this.repositorio = new ManutencaoRepositorioArquivo();
    }

    private void validarId(Integer id){
        for (Manutencao manutencao : manutencoes){
            if(manutencao.getId().equals(id)){
                throw new RuntimeException("ID já cadastrado");
            }
        }
    }

    public void cadastrarSimples(Integer id, Integer idEquipamento, String descricao, Double valorBase, boolean concluido){
        Equipamento equipamento = es.buscarPorId(idEquipamento);
        validarId(id);
        Manutencao e = new ManutencaoSimples(id,equipamento,descricao,valorBase,concluido);
        manutencoes.add(e);
    }

    public void cadastrarRecorrente(Integer id,Integer idEquipamento, String descricao, Double valorBase, boolean concluido, double desconto){
        Equipamento equipamento = es.buscarPorId(idEquipamento);
        validarId(id);
        Manutencao e = new ManutencaoRecorrente(id,equipamento,descricao,valorBase,concluido, desconto);
        manutencoes.add(e);
    }

    public void cadastrarUrgente(Integer id,Integer idEquipamento, String descricao, Double valorBase, boolean concluido, double taxa){
        Equipamento equipamento = es.buscarPorId(idEquipamento);
        validarId(id);
        Manutencao e = new ManutencaoUrgente(id,equipamento,descricao,valorBase,concluido, taxa);
        manutencoes.add(e);
    }

    public ArrayList<Manutencao> listar(){
        return manutencoes;
    }

    public ArrayList<Manutencao> listarPendentes(){
        ArrayList<Manutencao> pendentes = new ArrayList<>();
        for (Manutencao manutencao : manutencoes){
            if(manutencao.isConcluido() == false){
                pendentes.add(manutencao);
            }
        }
        return pendentes;
    }
    public Manutencao buscarPorId(Integer id){
        for (Manutencao manutencao : manutencoes){
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
        }else manutencoes.remove(manutencao);
    }

    public void carregar(){
        this.manutencoes = repositorio.carregar(es.listar());
    }

    public void salvar(){
        try{
            repositorio.salvar(this.manutencoes);
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }



}
