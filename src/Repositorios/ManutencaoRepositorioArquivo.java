package Repositorios;

import Entidades.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManutencaoRepositorioArquivo {
    private final String caminho = "manutencaos.csv";

    public void salvar(List<Manutencao> manutencaos){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            String linha;
            for(Manutencao e : manutencaos){
                if(e instanceof ManutencaoSimples){
                    linha = e.getClass().getSimpleName() + ";" + e.getId() +
                            ";" + e.getEquipamento().getId() +
                            ";" + e.getDescricao() +
                            ";" + e.getValorBase() +
                            ";" + e.isConcluido();

                } else if (e instanceof ManutencaoRecorrente) {
                    linha = e.getClass().getSimpleName() + ";" + e.getId() +
                            ";" + e.getEquipamento().getId() +
                            ";" + e.getDescricao() +
                            ";" + e.getValorBase() +
                            ";" + e.isConcluido() +
                            ";" + ((ManutencaoRecorrente) e).getDesconto();

                }else {
                    linha = e.getClass().getSimpleName() + ";" + e.getId() +
                            ";" + e.getEquipamento().getId() +
                            ";" + e.getDescricao() +
                            ";" + e.getValorBase() +
                            ";" + e.isConcluido() +
                            ";" + ((ManutencaoUrgente) e).getTaxaUrgencia();

            }
                bw.write(linha);
                bw.newLine();
            }

        }
     catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Manutencao> carregar(){
        File arquivo = new File(caminho);
        if (!arquivo.exists()){
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;
            ArrayList<Equipamento> lista = new ArrayList<>();
            while ((linha = br.readLine()) != null){
                String equipamento[] = linha.split(";");

                Equipamento e = new Equipamento(Integer.parseInt(equipamento[0]),equipamento[1],equipamento[2]);
                lista.add(e);

            }
            return lista;


        } catch (IOException e) {
            throw new RuntimeException(e);




}

