package gui;

import Entidades.Manutencao;
import Servicos.ManutencaoServicos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaManutencoes extends JDialog {

    private ManutencaoServicos manutencao;
    private JTable table;
    private DefaultTableModel model;
    private boolean listarTodas;


    public ListaManutencoes(JFrame parent, ManutencaoServicos manutencao, boolean listarTodas) {
        super(parent, listarTodas ? "Lista de Todas as Manutenções" : "Lista de Manutenções Pendentes", true);
        this.manutencao = manutencao;
        this.listarTodas = listarTodas;

        setSize(800, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel(listarTodas ? "Todas as Manutenções Registradas" : "Manutenções Pendentes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);


        String[] colunas = {"ID", "Equipamento", "Descrição", "Data", "Valor Base", "Tipo", "Concluída", "Custo Total"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnConcluir = new JButton("Marcar como Concluída");
        btnConcluir.setBackground(new Color(50, 150, 220)); // Cor azul
        btnConcluir.setForeground(Color.WHITE);
        painelBotoes.add(btnConcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDados();

        btnConcluir.addActionListener(e -> concluirManutencao());

        btnConcluir.setVisible(!listarTodas);
    }


    private void carregarDados() {
        model.setRowCount(0);
        List<Manutencao> manutencoes = listarTodas
                ? manutencao.listar()
                : manutencao.listarPendentes();

        for (Manutencao m : manutencoes) {

            String tipo = m.getClass().getSimpleName().replace("Manutencao", "");

            model.addRow(new Object[]{
                    m.getId(),
                    m.getEquipamento().getNome(),
                    m.getDescricao(),
                    String.format("R$ %.2f", m.getValorBase()),
                    tipo,
                    m.isConcluido() ? "Sim" : "Não",
                    String.format("R$ %.2f", m.calcularCusto())
            });
        }
    }


    private void concluirManutencao() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma manutenção para concluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) model.getValueAt(linhaSelecionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja marcar a Manutenção ID: " + id + " como CONCLUÍDA?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {

                manutencao.concluirManutencao(id);
                JOptionPane.showMessageDialog(this, "Manutenção concluída com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados(); // Recarrega a lista
            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Conclusão", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
