package gui;

import Servicos.EquipamentoServicos;
import Servicos.ManutencaoServicos;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private EquipamentoServicos equipamento;
    private ManutencaoServicos manutencao;

    public TelaPrincipal(EquipamentoServicos equipamento,  ManutencaoServicos manutencao){
        this.equipamento = equipamento;
        this.manutencao = manutencao;

        setTitle("Sistema de Gestão de Manutenção de Equipamentos Mecânicos");
        setSize(500, 350);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 1, 10, 10));


        JButton btnCadastrarEquipamento = new JButton("Cadastrar Equipamento");
        JButton btnListarEquipamentos = new JButton("Listar ou Remover Equipamentos");
        JButton btnCadastrarManutencao = new JButton("Cadastrar Manutenção");
        JButton btnListarManutencoes = new JButton(" Todas as Manutenções");
        JButton btnListarPendentes = new JButton("Listar Manutenções Pendentes (e Concluir)");
        JButton btnSair = new JButton("Sair");

        add(btnCadastrarEquipamento);
        add(btnListarEquipamentos);
        add(btnCadastrarManutencao);
        add(btnListarManutencoes);
        add(btnListarPendentes);
        add(btnSair);

        btnCadastrarEquipamento.addActionListener(e -> new FormularioEquipamento(this, equipamento).setVisible(true));
        btnListarEquipamentos.addActionListener(e -> new ListaEquipamentos(this, equipamento).setVisible(true));
        btnCadastrarManutencao.addActionListener(e -> new FormularioManutencao(this, equipamento, manutencao).setVisible(true));
        btnListarManutencoes.addActionListener(e -> new ListaManutencoes(this, manutencao, true).setVisible(true));
        btnListarPendentes.addActionListener(e -> new ListaManutencoes(this, manutencao, false).setVisible(true));
        btnSair.addActionListener(e -> sair());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                sair();
            }
        });
    }


    private void sair() {

        equipamento.salvar();
        manutencao.salvar();
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }


    public static void main(String[] args) {

        EquipamentoServicos equipamento = new EquipamentoServicos();
        ManutencaoServicos manutencao = new ManutencaoServicos(equipamento);


        equipamento.carregar();
        manutencao.carregar();


        SwingUtilities.invokeLater(() -> new TelaPrincipal(equipamento, manutencao).setVisible(true));
    }
}
