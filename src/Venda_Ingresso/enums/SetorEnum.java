package Venda_Ingresso.enums;

public enum SetorEnum {
    AMARELO("Amarelo", 180.00, 100),
    AZUL("Azul", 100.00, 100),
    BRANCO("Branco", 60.00, 100),
    VERDE("Verde", 350.00, 100);

    private String nome;
    private double valor;
    private int capacidade;

    SetorEnum(String nome, double valor, int capacidade) {
        this.nome = nome;
        this.valor = valor;
        this.capacidade = capacidade;
    }

    public double getValor() {
        return valor;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
