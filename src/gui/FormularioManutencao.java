package gui;

import Entidades.Equipamento;
import Servicos.EquipamentoServicos;
import Servicos.ManutencaoServicos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class FormularioManutencao extends JDialog {

    private EquipamentoServicos equipamento;
    private ManutencaoServicos manutencao;

    private JTextField txtId, txtDescricao, txtData, txtValorBase;
    private JComboBox<Equipamento> cmbEquipamento;
    private JComboBox<String> cmbTipoManutencao;

    private JPanel painelExtra;
    private JTextField txtValorExtra;
    private JLabel lblValorExtra;

    public FormularioManutencao(JFrame parent, EquipamentoServicos equipamento, ManutencaoServicos manutencao) {
        super(parent, "Cadastrar Manutenção", true);
        this.equipamento = equipamento;
        this.manutencao = manutencao;

        setSize(450, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCentral = new JPanel(new GridLayout(7, 2, 10, 10));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelCentral.add(new JLabel("ID da Manutenção:"));
        txtId = new JTextField();
        painelCentral.add(txtId);

        painelCentral.add(new JLabel("Equipamento:"));
        cmbEquipamento = new JComboBox<>();
        carregarEquipamentos();
        painelCentral.add(cmbEquipamento);

        painelCentral.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        painelCentral.add(txtDescricao);

        painelCentral.add(new JLabel("Valor Base (R$):"));
        txtValorBase = new JTextField();
        painelCentral.add(txtValorBase);

        painelCentral.add(new JLabel("Tipo de Manutenção:"));
        String[] tipos = {"Simples", "Recorrente", "Urgente"};
        cmbTipoManutencao = new JComboBox<>(tipos);
        cmbTipoManutencao.addActionListener(e -> atualizarCamposExtras());
        painelCentral.add(cmbTipoManutencao);

        painelExtra = new JPanel(new GridLayout(1, 2, 10, 10));
        lblValorExtra = new JLabel("");
        txtValorExtra = new JTextField();
        painelExtra.add(lblValorExtra);
        painelExtra.add(txtValorExtra);

        JPanel painelSul = new JPanel(new BorderLayout());
        painelSul.add(painelExtra, BorderLayout.NORTH);

        JButton btnSalvar = new JButton("Salvar Manutenção");
        btnSalvar.addActionListener(e -> cadastrarManutencao());

        painelSul.add(btnSalvar, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);
        add(painelSul, BorderLayout.SOUTH);

        atualizarCamposExtras();
    }


    private void carregarEquipamentos() {
        ArrayList<Equipamento> equipamentos = equipamento.listar();
        if (equipamentos.isEmpty()) {
            cmbEquipamento.addItem(new Equipamento(0, "Nenhum Equipamento Cadastrado", ""));
            cmbEquipamento.setEnabled(false);

            return;
        }
        for (Equipamento e : equipamentos) {
            cmbEquipamento.addItem(e);
        }
    }


    private void atualizarCamposExtras() {
        String tipoSelecionado = (String) cmbTipoManutencao.getSelectedItem();

        if ("Recorrente".equals(tipoSelecionado)) {
            lblValorExtra.setText("Desconto (%):");
            painelExtra.setVisible(true);
        } else if ("Urgente".equals(tipoSelecionado)) {
            lblValorExtra.setText("Taxa Urgência (%):");
            painelExtra.setVisible(true);
        } else {
            painelExtra.setVisible(false);
        }
        revalidate();
        repaint();
    }


    private void cadastrarManutencao() {
        try {

            Integer id = Integer.parseInt(txtId.getText());
            Integer equipamento = ((Equipamento) cmbEquipamento.getSelectedItem()).getId();

            if (equipamento == null || equipamento == 0) {
                JOptionPane.showMessageDialog(this, "Selecione um equipamento válido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String descricao = txtDescricao.getText();
            double valorBase = Double.parseDouble(txtValorBase.getText().replace(',', '.'));
            boolean concluida = false;
            String tipoSelecionado = (String) cmbTipoManutencao.getSelectedItem();


            switch (tipoSelecionado) {
                case "Simples":
                    manutencao.cadastrarSimples(id, equipamento, descricao, valorBase, concluida);
                    break;
                case "Recorrente":
                    double desconto = Double.parseDouble(txtValorExtra.getText().replace(',', '.'));
                    manutencao.cadastrarRecorrente(id, equipamento, descricao, valorBase, concluida, desconto);
                    break;
                case "Urgente":
                    double taxa = Double.parseDouble(txtValorExtra.getText().replace(',', '.'));
                    manutencao.cadastrarUrgente(id, equipamento, descricao, valorBase, concluida, taxa);
                    break;
            }


            JOptionPane.showMessageDialog(this, tipoSelecionado + " cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID, Valor Base, Taxa ou Desconto devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
         catch (RuntimeException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
