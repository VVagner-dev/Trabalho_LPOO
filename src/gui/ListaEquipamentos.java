package gui;

import Entidades.Equipamento;
import Servicos.EquipamentoServicos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaEquipamentos extends JDialog {

    private EquipamentoServicos equipamento;
    private JTable table;
    private DefaultTableModel model;

    public ListaEquipamentos(JFrame parent, EquipamentoServicos equipamento) {
        super(parent, "Lista de Equipamentos", true);
        this.equipamento = equipamento;

        setSize(500, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10)); // Adiciona margem na borda

        JLabel lblTitulo = new JLabel("Equipamentos Cadastrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Tipo"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnRemover = new JButton("Remover Selecionado");
        btnRemover.setBackground(new Color(220, 50, 50)); // Cor vermelha para indicar exclusão
        btnRemover.setForeground(Color.WHITE);
        painelBotoes.add(btnRemover);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDados();

        btnRemover.addActionListener(e -> removerEquipamento());
    }


    private void carregarDados() {
        model.setRowCount(0);
        List<Equipamento> equipamentos = equipamento.listar();

        for (Equipamento e : equipamentos) {
            model.addRow(new Object[]{
                    e.getId(),
                    e.getNome(),
                    e.getTipo()
            });
        }
    }


    private void removerEquipamento() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um equipamento para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) model.getValueAt(linhaSelecionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover o equipamento ID: " + id + "?",
                "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                equipamento.removerPorId(id);
                JOptionPane.showMessageDialog(this, "Equipamento removido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            }

            catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Remoção", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
