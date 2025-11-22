package Servicos;

import Entidades.Equipamento;

import java.util.ArrayList;

public class EquipamentoServicos {

    ArrayList<Equipamento> equipamentos = new ArrayList<>();

    public void cadastrar(Integer id, String nome, String tipo){

        for (Equipamento equipamento : equipamentos){
            if(equipamento.getId().equals(id)){
                throw new RuntimeException("ID já cadastrado");
            }
        }
        Equipamento e = new Equipamento(id, nome, tipo);
        equipamentos.add(e);
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



}
