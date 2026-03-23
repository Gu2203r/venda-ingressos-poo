/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso.services;

import Venda_Ingresso.entities.Ingresso;
import Venda_Ingresso.enums.SetorEnum;
import Venda_Ingresso.exceptions.SetorEsgotadoException;

import java.util.ArrayList;

/**
 *
 * @author Junior
 */
public class GerenciadorIngresso {

//    private static int maximoIngressos = 100; //Limite máximo de ingressos por setor
    private ArrayList<Ingresso> ingressos;
    private int prox = 0;

    private GerenciadorArquivo gerenciadorArquivo = new GerenciadorArquivo();
    private final String CAMINHO_ARQUIVO = "ingressos.ser";

    public GerenciadorIngresso() {

        // Tenta carregar os ingressos do arquivo, se o arquivo não existir ou estiver vazio, inicia com uma lista vazia
        ingressos = gerenciadorArquivo.desserializar(CAMINHO_ARQUIVO);

        // Se a lista de ingressos não estiver vazia, define o próximo código com base no último ingresso vendido
        if (!ingressos.isEmpty()) {
            prox = ingressos.get(ingressos.size() - 1).getCodigo();
        }
    }
    
    public synchronized boolean comprarIngresso(Ingresso ingresso) throws SetorEsgotadoException {
        if (ingresso == null) {
            return false;
        }

        String setorNovoIngresso = ingresso.getSetor();
        int quantidadeNovaCompra = ingresso.getQuantidade();
        int quantidadeVendidaNoSetor = 0;

        for (Ingresso ingressoVendido : ingressos) {
            if (ingressoVendido.getSetor() != null
                    && ingressoVendido.getSetor().equalsIgnoreCase(setorNovoIngresso)) {
                quantidadeVendidaNoSetor += ingressoVendido.getQuantidade();
            }
        }

        // Bloqueia compra que ultrapassa o limite maximo permitido no setor

        SetorEnum setorEnum = SetorEnum.valueOf(setorNovoIngresso.toUpperCase());

        if (quantidadeVendidaNoSetor + quantidadeNovaCompra > setorEnum.getCapacidade()) {
            throw new SetorEsgotadoException("O limite de " + setorEnum.getCapacidade() + " ingressos para o setor " + setorNovoIngresso + " foi atingido.");
        }

        ingresso.setThreadOrigem(Thread.currentThread().getName());

        ingresso.setThreadOrigem(Thread.currentThread().getName());

        ingresso.setCodigo(++prox);
        ingressos.add(ingresso); //Adiciona um elemento ao final do ArrayList

        // Salva os ingressos atualizados no arquivo
        gerenciadorArquivo.serializar(ingressos, CAMINHO_ARQUIVO);

        return true;
    }
    
    // Retorna os ingressos adquiridos
    public synchronized ArrayList<Ingresso> getIngressos() {
        return ingressos;
    }

}
