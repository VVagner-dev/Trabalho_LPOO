package gui;

import Servicos.EquipamentoServicos;
import javax.swing.*;
import java.awt.*;

public class FormularioEquipamento extends JDialog {

    private EquipamentoServicos equipamentoServicos;
    private JTextField txtId, txtNome, txtTipo;

    public FormularioEquipamento(JFrame parent, EquipamentoServicos equipamentoServicos) {
        super(parent, "Cadastrar Equipamento", true);
        this.equipamentoServicos = equipamentoServicos;

        setSize(350, 220);
        setLocationRelativeTo(parent);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("ID:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtId = new JTextField(15);
        add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(15);
        add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtTipo = new JTextField(15);
        add(txtTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 10, 5, 10);
        JButton btnSalvar = new JButton("Salvar Equipamento");
        add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> cadastrarEquipamento());
    }

    private void cadastrarEquipamento() {
        try {
            Integer id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String tipo = txtTipo.getText();

            if (nome.trim().isEmpty() || tipo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e Tipo não podem estar vazios.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            equipamentoServicos.cadastrar(id, nome, tipo);

            JOptionPane.showMessageDialog(this, "Equipamento cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this, "ID inválido. O campo deve conter apenas números.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
