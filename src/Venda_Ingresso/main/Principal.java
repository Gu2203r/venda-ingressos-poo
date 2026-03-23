/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso.main;

import Venda_Ingresso.entities.Ingresso;
import Venda_Ingresso.enums.SetorEnum;
import Venda_Ingresso.services.CompradorRunnable;
import Venda_Ingresso.services.GerenciadorArquivo;
import Venda_Ingresso.services.GerenciadorIngresso;
import Venda_Ingresso.ui.TelaInicial;

/**
 *
 * @author Junior
 */
public class Principal {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TelaInicial telaInicial = new TelaInicial();

        GerenciadorIngresso gerenciador = new GerenciadorIngresso();

        System.out.println("=== INICIANDO TESTE DE CONCORRÊNCIA ===");


        // Vamos forçar um gargalo no setor AZUL (limite de 100)
        Runnable comprador1 = new CompradorRunnable("João", SetorEnum.AZUL, 40, gerenciador);
        Runnable comprador2 = new CompradorRunnable("Maria", SetorEnum.AZUL, 40, gerenciador);
        Runnable comprador3 = new CompradorRunnable("Carlos", SetorEnum.AZUL, 30, gerenciador); // Esta ou outra deve falhar
        Runnable comprador4 = new CompradorRunnable("Ana", SetorEnum.AMARELO, 10, gerenciador); // Setor diferente, deve passar livre

        Thread t1 = new Thread(comprador1, "Thread-AppMobile");
        Thread t2 = new Thread(comprador2, "Thread-SiteWeb");
        Thread t3 = new Thread(comprador3, "Thread-BilheteriaFisica");
        Thread t4 = new Thread(comprador4, "Thread-ParceiroVIP");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            System.out.println("\n>>> Todas as threads finalizaram o processamento. <<<\n");
        } catch (InterruptedException e) {
            System.err.println("A thread principal foi interrompida enquanto aguardava.");
            Thread.currentThread().interrupt();
        }

        System.out.println("=== RELATÓRIO FINAL DE VENDAS ===");
        System.out.println("Total de registros no sistema: " + gerenciador.getIngressos().size());

        for (Ingresso ing : gerenciador.getIngressos()) {
            System.out.println(
                    "Código: " + ing.getCodigo() +
                            " | Comprador: " + ing.getNome() +
                            " | Setor: " + ing.getSetor() +
                            " | Qtd: " + ing.getQuantidade() +
                            " | Thread Origem: " + ing.getThreadOrigem()
            );
        }
    }
    
}
