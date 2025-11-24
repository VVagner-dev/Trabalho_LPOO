package Repositorios;

import Entidades.Equipamento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoRepositorioArquivo {

    //caminho do arquivo
    private final String caminho = "equipamentos.csv";

    public ArrayList<Equipamento> carregar(){
        File arquivo = new File(caminho);
        if (!arquivo.exists()){             //verifica se o arquivo existe se não ele retona uma lista vazia
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))){ //conectando o BufferedReader ao arquivo
            String linha;
            ArrayList<Equipamento> lista = new ArrayList<>();

            while ((linha = br.readLine()) != null){ //faça ate o final do arquivo
                String equipamento[] = linha.split(";");  //divisão de acordo com o ; para entrar em um veto

                Equipamento e = new Equipamento(Integer.parseInt(equipamento[0]),equipamento[1],equipamento[2]); // vai pegar os atributos do vetor e vai instaciar
                lista.add(e);

            }
            return lista; //vai retorna todos da lista


        } catch (IOException e) { //tratação de erro do BufferedReader
            throw new RuntimeException("Erro ao carregar equipamentos: "+ e.getMessage());
        }
    }

    public void salvar(ArrayList<Equipamento> equipamentos){


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) { //conectando o BufferedWriter ao arquivo

            for(Equipamento e : equipamentos){ //for each para cada equipamento da lista
                String linha = e.getId() + ";" + e.getNome() + ";" + e.getTipo(); //estruturação com a divisão de acordo com ;

                bw.write(linha); //vai escrevar a linha
                bw.newLine();    //vai pular a linha
            }
        } catch (IOException e) { // tratação de erro do BufferedWriter
            throw new RuntimeException("Erro ao salvar arquivo: "+ e.getMessage());
        }
    }

}
