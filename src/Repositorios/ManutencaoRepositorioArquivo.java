package Repositorios;

import Entidades.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManutencaoRepositorioArquivo {
    private final String caminho = "manutencoes.csv";

    public ArrayList<Manutencao> carregar(ArrayList<Equipamento> equipamentos) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {    //verifica se o arquivo existe se não ele retona uma lista vazia
            return new ArrayList<>();
        }

        ArrayList<Manutencao> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) { //conectando o BufferedReader ao arquivo
            String linha;
            Manutencao m = null;

            while ((linha = br.readLine()) != null) { //faça ate o final do arquivo
                String manutencao[] = linha.split(";"); //divisão de acordo com o ; para entrar em um veto

                Equipamento encontrado = buscarPorId(equipamentos, Integer.valueOf(manutencao[2]));
                // vai colocar o equipamento retornado pelo id no "Equipamento encontrado"

                if(encontrado == null) continue; //se "encontrado" for null vai pular a linha

                if (manutencao[0].equals("ManutencaoSimples")) { // se o objeto for do tipo "ManutencaoSimples"
                        m = new ManutencaoSimples(
                                Integer.valueOf(manutencao[1]),
                                encontrado,
                                manutencao[3],
                                Double.valueOf(manutencao[4]),
                                Boolean.valueOf(manutencao[5]));

                }
                else if (manutencao[0].equals("ManutencaoRecorrente")) { // se o objeto for do tipo "ManutencaoRecorrente"
                    m = new ManutencaoRecorrente(
                            Integer.valueOf(manutencao[1]),
                            encontrado,
                            manutencao[3],
                            Double.valueOf(manutencao[4]),
                            Boolean.valueOf(manutencao[5]),
                            Double.valueOf(manutencao[6]));

                }
                else {
                    if(buscarPorId(equipamentos,Integer.valueOf(manutencao[2]))!= null) { //se não for nenhum dos dois vai ser ManutencaoUrgente
                    m = new ManutencaoUrgente(Integer.valueOf(manutencao[1]),
                            encontrado,
                            manutencao[3],
                            Double.valueOf(manutencao[4]),
                            Boolean.valueOf(manutencao[5]),
                            Double.valueOf(manutencao[6]));
                    }
                }
                lista.add(m); //adiciona na lista o objeto instanciado nos if


            }

        // tratação de erro do BufferedReader
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return lista;


    }

    public void salvar(List<Manutencao> manutencoes){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) { //conectando o BufferedWriter ao arquivo
            String linha;
            for(Manutencao e : manutencoes){ //for each para cada manutenção da lista

                if(e instanceof ManutencaoSimples){ // se o objeto for do tipo "ManutencaoSimples"
                    linha = e.getClass().getSimpleName() + ";" + e.getId() +
                            ";" + e.getEquipamento().getId() +
                            ";" + e.getDescricao() +
                            ";" + e.getValorBase() +
                            ";" + e.isConcluido();

                } else if (e instanceof ManutencaoRecorrente) { // se o objeto for do tipo "ManutencaoRecorrente"
                    linha = e.getClass().getSimpleName() + ";" + e.getId() +
                            ";" + e.getEquipamento().getId() +
                            ";" + e.getDescricao() +
                            ";" + e.getValorBase() +
                            ";" + e.isConcluido() +
                            ";" + ((ManutencaoRecorrente) e).getDesconto();

                }else { //se não for nenhum dos dois vai ser ManutencaoUrgente
                    linha = e.getClass().getSimpleName() + ";" + e.getId() +
                            ";" + e.getEquipamento().getId() +
                            ";" + e.getDescricao() +
                            ";" + e.getValorBase() +
                            ";" + e.isConcluido() +
                            ";" + ((ManutencaoUrgente) e).getTaxaUrgencia();

                }
                bw.write(linha); // vai escrever o objeto instaciado nos if no arquivo
                bw.newLine(); //quebra de linha do arquivo
            }

        }
        //tratação de erro do BufferedWriter
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //vai buscar por id do equipamento pela lista de equipamentos se não encontrar retorna null
    public Equipamento buscarPorId(List<Equipamento> equipamentos ,Integer id){

        for (Equipamento equipamento : equipamentos){
            if(equipamento.getId().equals(id)){
                return equipamento;
            }
        }
        return null;

    }
}

