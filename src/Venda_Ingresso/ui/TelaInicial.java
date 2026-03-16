/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso.ui;

import Venda_Ingresso.services.GerenciadorIngresso;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Junior
 */
public class TelaInicial extends JDialog {

    private JPanel painelFundo;
    private JButton btnComprar;
    private JButton btnGerarRelatorio;


    private GerenciadorIngresso gerenciador = new GerenciadorIngresso();

    public TelaInicial() {
        criarComponentesTela();
    }

    private void criarComponentesTela() {
        btnComprar = new JButton("Comprar Ingresso");
        btnGerarRelatorio = new JButton("Gerar Relatório");
        btnGerarRelatorio = new JButton("Exportar Relatório");

        btnComprar.addActionListener((e) -> {
            JanelaCadastroIngresso janelaCadastroIngresso = new JanelaCadastroIngresso(gerenciador);
            janelaCadastroIngresso.setVisible(true);
        });

        btnGerarRelatorio.addActionListener((e) -> {
            JanelaGrafica janelaGrafica = new JanelaGrafica();
            janelaGrafica.imprimirRelatorio(gerenciador.getIngressos());
            janelaGrafica.setVisible(true);
        });

        painelFundo = new JPanel();
        painelFundo.add(btnComprar);
        painelFundo.add(btnGerarRelatorio);

        add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setSize(300, 200);
        setVisible(true);
    }
}
