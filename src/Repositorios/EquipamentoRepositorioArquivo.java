package Repositorios;

import Entidades.Equipamento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoRepositorioArquivo {

    private final String caminho = "equipamentos.csv";

    public void salvar(List<Equipamento> equipamentos){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for(Equipamento e : equipamentos){
                String linha = e.getId() + ";" + e.getNome() + ";" + e.getTipo();

                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: "+ e.getMessage());
        }

    }

    public ArrayList<Equipamento> carregar(){
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
    }

}
