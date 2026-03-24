/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso.ui;

import Venda_Ingresso.services.GerenciadorIngresso;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Junior
 */
public class TelaInicial extends JDialog {

    private JPanel painelFundo;
    private JButton btnComprar;
    private JButton btnGerarRelatorio;
    private GerenciadorIngresso gerenciador;

    public TelaInicial(GerenciadorIngresso gerenciador){
        this.gerenciador = gerenciador;
        criarComponentesTela();
    }

    private void criarComponentesTela() {
        btnComprar = new JButton("Comprar Ingresso");
        btnGerarRelatorio = new JButton("Gerar Relatório");

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
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JanelaGrafica janelaGrafica = new JanelaGrafica();
                janelaGrafica.imprimirRelatorio(gerenciador.getIngressos());
                janelaGrafica.setTitle("Relatorio Final!!!");
                janelaGrafica.setVisible(true);
                dispose();
            }
        });

        setLocationRelativeTo(null);
        pack();
        setSize(300, 200);
        setVisible(true);
    }
}
