package Venda_Ingresso.services;

import Venda_Ingresso.enums.SetorEnum;

public class CompradorRunnable implements Runnable{

    String nomeComprador;
    SetorEnum setorEnum;
    int quantidade;
    GerenciadorIngresso gerenciador;

    CompradorRunnable(String nomeComprador, SetorEnum setorEnum, int quantidade, GerenciadorIngresso gerenciador) {
        this.nomeComprador = nomeComprador;
        this.setorEnum = setorEnum;
        this.quantidade = quantidade;
        this.gerenciador = gerenciador;
    }

    @Override
    public void run() {

    }
}
