package Venda_Ingresso.services;

import Venda_Ingresso.entities.Ingresso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GerenciadorArquivo {

    // Método para gravar a lista no arquivo .ser
    public void serializar(List<Ingresso> ingressos, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(ingressos);
            System.out.println("Dados salvos com sucesso em: " + path);
        } catch (IOException e) {
            System.err.println("Erro ao serializar os ingressos: " + e.getMessage());   
        }
    }

    // Método para ler a lista do arquivo .ser
    public ArrayList<Ingresso> desserializar(String path) {
        ArrayList<Ingresso> ingressos = new ArrayList<>();
        File arquivo = new File(path);

        // Se for a primeira vez rodando o programa, o arquivo não existirá.
        // Retornamos a lista vazia para evitar erro de FileNotFound.
        if (!arquivo.exists()) {
            return ingressos;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            // Fazemos o cast (conversão) do Object lido para ArrayList<Ingresso>
            ingressos = (ArrayList<Ingresso>) ois.readObject();
            System.out.println("Dados carregados com sucesso de: " + path);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao desserializar os ingressos: " + e.getMessage());
        }

        return ingressos;
    }

    // Método para exportar a lista em um arquivo .txt legível para humanos
    public void exportarTxt(List<Ingresso> ingressos, String path) {
        // O bloco try-with-resources garante que o arquivo será fechado corretamente após o uso
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            // Escreve um cabeçalho bonitinho para o arquivo
            bw.write("========================================");
            bw.newLine(); // Pula para a próxima linha
            bw.write("        RELATÓRIO DE INGRESSOS");
            bw.newLine();
            bw.write("========================================");
            bw.newLine();
            bw.newLine();

            // Verifica se a lista está vazia
            if (ingressos.isEmpty()) {
                bw.write("Nenhum ingresso vendido até o momento.");
                bw.newLine();
            } else {
                // Percorre a lista e escreve os dados de cada ingresso
                for (Ingresso ingresso : ingressos) {
                    bw.write("Código: " + ingresso.getCodigo());
                    bw.newLine();
                    bw.write("Nome: " + ingresso.getNome());
                    bw.newLine();
                    bw.write("Setor: " + ingresso.getSetor());
                    bw.newLine();
                    bw.write("Quantidade: " + ingresso.getQuantidade());
                    bw.newLine();

                    // String.format ajuda a deixar o valor formatado com 2 casas decimais
                    bw.write(String.format("Valor Unitário: R$ %.2f", ingresso.getValor()));
                    bw.newLine();
                    bw.write(String.format("Valor Total: R$ %.2f", ingresso.getValorTotal()));
                    bw.newLine();
                    bw.write("Data/Hora: " + ingresso.getDataHora());
                    bw.newLine();

                    // Linha separadora entre um ingresso e outro
                    bw.write("----------------------------------------");
                    bw.newLine();
                }
            }

            System.out.println("Relatório TXT gerado com sucesso em: " + path);

        } catch (IOException e) {
            System.err.println("Erro ao exportar relatório TXT: " + e.getMessage());
        }
    }
}