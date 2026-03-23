package Venda_Ingresso.services;

import Venda_Ingresso.entities.Ingresso;
import Venda_Ingresso.enums.SetorEnum;
import Venda_Ingresso.exceptions.SetorEsgotadoException;

import java.time.LocalDateTime;

public class CompradorRunnable implements Runnable{

    private String nomeComprador;
    private SetorEnum setor;
    private int quantidade;
    private GerenciadorIngresso gerenciador;

    public CompradorRunnable(String nomeComprador, SetorEnum setor, int quantidade, GerenciadorIngresso gerenciador) {
        this.nomeComprador = nomeComprador;
        this.setor = setor;
        this.quantidade = quantidade;
        this.gerenciador = gerenciador;
    }

    @Override
    public void run() {
        System.out.println("Thread iniciada: " + nomeComprador + " está tentando comprar " + quantidade + " ingresso(s) no setor " + setor.name() + "...");

        // Simula latência de rede
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.err.println("Thread de " + nomeComprador + " foi interrompida durante a latência de rede.");
            Thread.currentThread().interrupt();
            return;
        }

        Ingresso ingresso = new Ingresso();
        ingresso.setNome(nomeComprador);
        ingresso.setSetor(setor.name());

        if (quantidade <= 0) {
            System.err.println("Erro para " + nomeComprador + ": A quantidade deve ser maior que zero.");
            return;
        }
        ingresso.setQuantidade(quantidade);

        double valorUnitario = setor.getValor();
        ingresso.setValor(valorUnitario);
        ingresso.setValorTotal(valorUnitario * quantidade);
        ingresso.setDataHora(LocalDateTime.now());

        try {
            boolean sucesso = gerenciador.comprarIngresso(ingresso);
            if (sucesso) {
                System.out.println(">>> SUCESSO: " + nomeComprador + " garantiu seu(s) ingresso(s)!");
            }
        } catch (SetorEsgotadoException e) {
            System.err.println(">>> FALHA: " + nomeComprador + " não conseguiu comprar. " + e.getMessage());
        } catch (Exception e) {
            System.err.println(">>> ERRO INESPERADO para " + nomeComprador + ": " + e.getMessage());
        }
    }
}
