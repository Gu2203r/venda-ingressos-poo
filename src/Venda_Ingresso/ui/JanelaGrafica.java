/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso.ui;

import Venda_Ingresso.services.GerenciadorIngresso;
import Venda_Ingresso.entities.Ingresso;
import Venda_Ingresso.services.GerenciadorArquivo;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Junior
 */
class JanelaGrafica extends JDialog{
    
    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JTable tabelaIngressos;
    private JScrollPane scroll;    
    private DefaultTableModel modelo;//Modelo da tabela

    private GerenciadorIngresso gerenciador;

    private JButton btnExportarTxt;
    private ArrayList<Ingresso> listaIngressosParaExportar;
    
    public JanelaGrafica(TelaInicial telaInicial, boolean isModal) {
        super(telaInicial, isModal);
        criarTabela();
        criarComponentes();
    }
    
    public JanelaGrafica(){
        modelo = new DefaultTableModel();
        criarTabela();
        criarComponentes();
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);//Cria a tabela
        tabelaIngressos.setSize(700, 800);
        
        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Setor");
        modelo.addColumn("Qtd");
        modelo.addColumn("Valor");
        modelo.addColumn("Total");
        modelo.addColumn("Data e Hora");
        
         tabelaIngressos.getColumnModel().getColumn(0)//primeira coluna
                .setPreferredWidth(5);//largura da coluna
         tabelaIngressos.getColumnModel().getColumn(1)
                .setPreferredWidth(70);
         tabelaIngressos.getColumnModel().getColumn(2)
                .setPreferredWidth(20);
         tabelaIngressos.getColumnModel().getColumn(3)
                .setPreferredWidth(1);         
         tabelaIngressos.getColumnModel().getColumn(4)
                .setPreferredWidth(15);
         tabelaIngressos.getColumnModel().getColumn(5)
                .setPreferredWidth(15);
         tabelaIngressos.getColumnModel().getColumn(6)
                .setPreferredWidth(70);
    }

    private void criarComponentes() {

        painelBotoes = new JPanel();

        // --- INÍCIO DA CRIAÇÃO DO BOTÃO ---
        btnExportarTxt = new JButton("Exportar para TXT");

        btnExportarTxt.addActionListener((e) -> {
            // Verifica se a lista existe e tem dados
            if (listaIngressosParaExportar != null && !listaIngressosParaExportar.isEmpty()) {
                GerenciadorArquivo gerArq = new GerenciadorArquivo();
                String caminhoFicheiro = "relatorio_ingressos.txt";

                // Chama o método que criámos anteriormente
                gerArq.exportarTxt(listaIngressosParaExportar, caminhoFicheiro);

                // Mostra um aviso visual de sucesso
                JOptionPane.showMessageDialog(this,
                        "Relatório exportado com sucesso para o ficheiro:\n" + caminhoFicheiro,
                        "Exportação Concluída",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Não existem ingressos vendidos para exportar.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Adiciona o botão ao painel de botões
        painelBotoes.add(btnExportarTxt);
        // --- FIM DA CRIAÇÃO DO BOTÃO ---

        scroll = new JScrollPane(tabelaIngressos);
        painelFundo = new JPanel();
        painelFundo.add(scroll);
        painelFundo.add(painelBotoes);
        add(painelFundo);

        setTitle("Relatório de Ingressos");
        setVisible(true);
        pack();
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void carregarDados(DefaultTableModel modelo, ArrayList<Ingresso> ingressos) {
        modelo.setNumRows(0);//Seta o número de colunas - Limpa a tabela
        
        ingressos.forEach(c -> { //lambda
            modelo.addRow(new Object[]{//instância um novo vetor com os parâmetros
                c.getCodigo(),//cada contato adiciona uma nova linha
                c.getNome(),
                c.getSetor(),
                c.getQuantidade(),
                c.getValor(),
                c.getValorTotal(),
                c.getDataHora()
            });
        });
    }
    
    public GerenciadorIngresso getGerenciador() {
        return gerenciador;
    }

    public void imprimirRelatorio(ArrayList<Ingresso> ingressos) {
        this.listaIngressosParaExportar = ingressos;

        carregarDados(modelo, ingressos);
    }
    
}
