package Servicos;

import Entidades.Equipamento;
import Repositorios.EquipamentoRepositorioArquivo;

import java.util.ArrayList;

public class EquipamentoServicos {

    private ArrayList<Equipamento> equipamentos = new ArrayList<>();
    private EquipamentoRepositorioArquivo repositorio;

    public EquipamentoServicos() {
        this.repositorio = new EquipamentoRepositorioArquivo();
    }

    public void cadastrar(Integer id, String nome, String tipo){

        for (Equipamento equipamento : equipamentos){
            if(equipamento.getId().equals(id)){
                throw new RuntimeException("ID já cadastrado");
            }
        }
        Equipamento equipamento = new Equipamento(id, nome, tipo);
        equipamentos.add(equipamento);
    }

    public ArrayList<Equipamento> listar(){
        return equipamentos;
    }

    public Equipamento buscarPorId(Integer id){

        for (Equipamento equipamento : equipamentos){
            if(equipamento.getId().equals(id)){
                return equipamento;
            }
        }
        return null;

    }

    public void removerPorId(Integer id){
        Entidades.Equipamento equipamento = buscarPorId(id);
        if(equipamento == null){
            throw new RuntimeException("ID não encontrado");
        }else equipamentos.remove(equipamento);
    }

    public void carregar(){
        this.equipamentos = repositorio.carregar();
    }

    public void salvar(){
        try{
            repositorio.salvar(this.equipamentos);
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
