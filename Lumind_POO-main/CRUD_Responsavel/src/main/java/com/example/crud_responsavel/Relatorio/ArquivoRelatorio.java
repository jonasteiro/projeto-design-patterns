package com.example.crud_responsavel.Relatorio;


import java.io.*;
import java.util.ArrayList;

/**
 * Classe responsável por salvar e ler a lista de Relatórios em um arquivo
 * no disco (persistência simples via serialização de objetos Java).
 * Segue exatamente o mesmo padrão da classe ArquivoAtividade.
 */
public class ArquivoRelatorio {

    // Nome do arquivo onde a lista de relatórios é salva no disco
    private static final String CAMINHO_ARQUIVO = "Relatorio.dat";

    /**
     * Salva (sobrescreve) a lista INTEIRA de relatórios no arquivo.
     * Toda vez que alguém adiciona, altera ou exclui, chama esse método.
     */
    public static void salvarLista(ArrayList<Relatorio> relatorios) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile(); // cria o arquivo se ainda não existir
            }
            // ObjectOutputStream = fluxo que serializa (converte) objetos Java para bytes e grava no arquivo
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(relatorios); // escreve a lista inteira de uma vez
            oos.close();

        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    /**
     * Adiciona um único relatório novo na lista e salva no arquivo.
     * Lê a lista atual, acrescenta o novo item e regrava tudo.
     */
    public static void adicionarRelatorio(Relatorio relatorio) {
        ArrayList<Relatorio> relatorios = lerLista(); // 1. Lê o que já tem
        relatorios.add(relatorio);                    // 2. Adiciona o novo
        salvarLista(relatorios);                      // 3. Salva tudo de volta
    }

    /**
     * Lê e retorna a lista completa de relatórios do arquivo.
     * Se o arquivo não existir ainda, retorna uma lista vazia.
     */
    public static ArrayList<Relatorio> lerLista() {
        ArrayList<Relatorio> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                // ObjectInputStream = fluxo que lê bytes do arquivo e reconstrói objetos Java
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<Relatorio>) ois.readObject(); // lê a lista inteira de uma vez
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Atualiza (substitui) um relatório existente pelo seu ID.
     * Retorna true se encontrou e atualizou, false se o ID não existir.
     */
    public static boolean atualizarRelatorio(Relatorio relatorioAtualizado) {
        ArrayList<Relatorio> lista = lerLista(); // 1. Puxa a lista do arquivo

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == relatorioAtualizado.getId()) { // 2. Achou o ID
                lista.set(i, relatorioAtualizado); // 3. Substitui o antigo pelo novo
                salvarLista(lista);                // 4. Salva no arquivo
                return true;                       // Avisa a tela que deu certo
            }
        }
        return false; // ID não encontrado na lista
    }

    /**
     * Remove (exclui) o relatório com o ID informado da lista e do arquivo.
     * Retorna true se encontrou e excluiu, false se o ID não existir.
     */
    public static boolean excluirRelatorio(int idParaExcluir) {
        ArrayList<Relatorio> lista = lerLista(); // 1. Puxa a lista

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == idParaExcluir) { // 2. Achou o ID
                lista.remove(i);   // 3. Remove da lista
                salvarLista(lista); // 4. Salva a lista atualizada no arquivo
                return true;        // Avisa a tela que deu certo
            }
        }
        return false; // ID não encontrado na lista
    }
}