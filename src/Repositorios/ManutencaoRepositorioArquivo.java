package Repositorios;

import Entidades.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManutencaoRepositorioArquivo {
    private final String caminho = "manutencoes.csv";

    public ArrayList<Manutencao> carregar(ArrayList<Equipamento> equipamentos) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        ArrayList<Manutencao> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            Manutencao m = null;

            while ((linha = br.readLine()) != null) {
                String manutencao[] = linha.split(";");

                Equipamento encontrado = buscarPorId(equipamentos, Integer.valueOf(manutencao[2]));

                if(encontrado == null) continue;

                if (manutencao[0].equals("ManutencaoSimples")) {
                        m = new ManutencaoSimples(
                                Integer.valueOf(manutencao[1]),
                                encontrado,
                                manutencao[3],
                                Double.valueOf(manutencao[4]),
                                Boolean.valueOf(manutencao[5]));

                }
                else if (manutencao[0].equals("ManutencaoRecorrente")) {
                    m = new ManutencaoRecorrente(
                            Integer.valueOf(manutencao[1]),
                            encontrado,
                            manutencao[3],
                            Double.valueOf(manutencao[4]),
                            Boolean.valueOf(manutencao[5]),
                            Double.valueOf(manutencao[6]));

                }
                else {
                    if(buscarPorId(equipamentos,Integer.valueOf(manutencao[2]))!= null) {
                    m = new ManutencaoUrgente(Integer.valueOf(manutencao[1]),
                            encontrado,
                            manutencao[3],
                            Double.valueOf(manutencao[4]),
                            Boolean.valueOf(manutencao[5]),
                            Double.valueOf(manutencao[6]));
                    }
                }
                lista.add(m);


            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return lista;


    }

    public void salvar(List<Manutencao> manutencoes){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            String linha;
            for(Manutencao e : manutencoes){
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
    public Equipamento buscarPorId(List<Equipamento> equipamentos ,Integer id){

        for (Equipamento equipamento : equipamentos){

            if(equipamento.getId().equals(id)){
                return equipamento;
            }
        }
        return null;

    }
}

