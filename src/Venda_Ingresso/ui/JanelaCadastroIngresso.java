/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso.ui;

import Venda_Ingresso.enums.SetorEnum;
import Venda_Ingresso.services.GerenciadorIngresso;
import Venda_Ingresso.entities.Ingresso;
import Venda_Ingresso.exceptions.QuantidadeInvalidaException;
import Venda_Ingresso.exceptions.SetorEsgotadoException;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Junior
 */
public class JanelaCadastroIngresso extends JDialog {
    
    private DefaultTableModel modelo;
    private JPanel painelFundo;
    private JButton btnSalvar;
    private JButton btnVoltarTelaInicial;
    private JLabel lblCodigo;
    private JLabel lblNome;
    private JLabel lblValor;
    private JLabel lblQtde;
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtSetor;
    private JTextField txtQtde;
    
    private String[] setores
            = {"Amarelo","Azul","Branco","Verde"};

    private JComboBox<String> cbxSetores;
    
    private String[] tiposTorcedor
            = {"Inteira", "Meia"};

    private JComboBox<String> cbxTipoTorcedor;

    GerenciadorIngresso gerenciador;

    String setor = "";
    String tipoTorcedor = "";

    public JanelaCadastroIngresso(GerenciadorIngresso gerenciadorCompartilhado) {
        this.gerenciador = gerenciadorCompartilhado;
        criarComponentesInsercao();
    }

    public JanelaCadastroIngresso() {
        criarComponentesInsercao();
    }

    private void limpar(){
        txtNome.setText("");
        txtQtde.setText("");        
    }

    // cria a tela onde coloca os dados do ingresso: nome,tipo etc.
    private void criarComponentesInsercao() {
        
        
        btnSalvar = new JButton("Salvar");        
        btnVoltarTelaInicial  = new JButton("Voltar para Tela Inicial");
        
        btnSalvar.addActionListener((e) -> {
           comprarIngresso();
        });

        btnVoltarTelaInicial.addActionListener((e) -> {
            dispose(); // Fecha e destrói esta janela, revelando a Tela Inicial que já estava no fundo
        });
        
        lblNome = new JLabel("Nome:");       
        cbxTipoTorcedor = new JComboBox(tiposTorcedor);
        lblQtde = new JLabel("Quantidade:");
        txtNome = new JTextField(10);        
        txtQtde = new JTextField(5);
        cbxSetores = new JComboBox(setores);

        painelFundo = new JPanel();
        painelFundo.add(lblNome);
        painelFundo.add(txtNome);   
        painelFundo.add(cbxTipoTorcedor);
        painelFundo.add(lblQtde);
        painelFundo.add(txtQtde);
        painelFundo.add(cbxSetores);
        painelFundo.add(btnSalvar);
        painelFundo.add(btnVoltarTelaInicial);

        add(painelFundo);        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);// Só fecha a janela(Esconde). Não fecha a aplicação(EXIT_ON_CLOSE)
        setLocationRelativeTo(null);
        setSize(800, 150); // Garante espaço suficiente para a linha de componentes
        setResizable(false); // Impede o usuário de quebrar o layout redimensionando
        setVisible(true);
    }

    private void comprarIngresso() {
        Ingresso ingresso = new Ingresso();
        double valorIngresso = 0.00;

        ingresso.setNome(txtNome.getText());
        setor = cbxSetores.getSelectedItem().toString();
        ingresso.setSetor(setor);

        // Verifica se a quantidade é um número inteiro válido
        int qtde;
        try {
            qtde = Integer.parseInt(txtQtde.getText());

            // Lança a exceção se for menor ou igual a zero
            if (qtde <= 0) {
                throw new QuantidadeInvalidaException("A quantidade de ingressos deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida! Por favor, insira um número inteiro.");
            return; // Interrompe a execução
        } catch (QuantidadeInvalidaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Quantidade", JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução
        }

        ingresso.setQuantidade(qtde);

        // Usa o Enum para identificar os valores dos ingressos, e define o valor do ingresso com base no setor selecionado
        SetorEnum setorSelecionado = SetorEnum.valueOf(setor.toUpperCase());
        valorIngresso = setorSelecionado.getValor();

        tipoTorcedor = cbxTipoTorcedor.getSelectedItem().toString();

        // Se for estudante ou aposentado, calcula meia entrada
        if (tipoTorcedor.equalsIgnoreCase("Meia")){
            valorIngresso = valorIngresso / 2;
        }

        ingresso.setValor(valorIngresso);

        // Calcula o valor total
        double valorTotal = ingresso.getValor() * ingresso.getQuantidade();
        ingresso.setValorTotal(valorTotal);

        // Captura a data e hora local da máquina
        ingresso.setDataHora(LocalDateTime.now());

        // Finaliza a compra
        try {
            if (gerenciador.comprarIngresso(ingresso)) {
                limpar();
                JOptionPane.showMessageDialog(null, "Ingresso comprado com sucesso!");
            }
        } catch (SetorEsgotadoException e) {
            limpar();
            // Mostra a mensagem exata que definimos lá na classe GerenciadorIngresso
            JOptionPane.showMessageDialog(null, e.getMessage(), "Setor Esgotado", JOptionPane.ERROR_MESSAGE);
        }
    }
}
