package com.example.crud_responsavel.Atividade;

import com.example.crud_responsavel.Observador;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe responsável por salvar e ler a lista de Atividades em um arquivo
 * no disco (persistência simples via serialização de objetos).
 * Segue exatamente o mesmo padrão da classe ArquivoResponsavel.
 */
public class ArquivoAtividade {

    // Nome do arquivo onde a lista de atividades é salva
    private static final String CAMINHO_ARQUIVO = "Atividade.dat";

    // ===================== PADRÃO OBSERVER =====================
    // Esta classe é o "Subject": guarda a lista de observadores
    // (telas interessadas) e avisa todos quando os dados são salvos.
    private static final ArrayList<Observador> observadores = new ArrayList<>();

    public static void adicionarObservador(Observador observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public static void removerObservador(Observador observador) {
        observadores.remove(observador);
    }

    private static void notificarObservadores() {
        // Percorre uma cópia, pra não dar erro se alguém se remover durante o aviso
        for (Observador observador : new ArrayList<>(observadores)) {
            observador.atualizar();
        }
    }

    // Salva (sobrescreve) a lista inteira de atividades no arquivo
    public static void salvarLista(ArrayList<Atividade> atividades) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(atividades);
            oos.close();

            // Dados gravados com sucesso: avisa as telas inscritas
            notificarObservadores();

        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    // Adiciona uma nova atividade na lista e salva no arquivo
    public static void adicionarAtividade(Atividade atividade) {
        ArrayList<Atividade> atividades = lerLista();
        atividades.add(atividade);
        salvarLista(atividades);
    }

    // Lê a lista completa de atividades do arquivo (vazia se ele não existir)
    public static ArrayList<Atividade> lerLista() {
        ArrayList<Atividade> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<Atividade>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    // --- Metodo para atualizar
    public static boolean atualizarAtividade(Atividade atividadeAtualizada) {
        ArrayList<Atividade> lista = lerLista(); // 1. Puxa a lista do arquivo

        for (int i = 0; i < lista.size(); i++) {
            // 2. Procura quem tem o mesmo ID
            if (lista.get(i).getId() == atividadeAtualizada.getId()) {
                lista.set(i, atividadeAtualizada); // 3. Substitui a antiga pela nova
                salvarLista(lista); // 4. Salva no arquivo
                return true; // Avisa a tela que deu certo
            }
        }
        return false; // ID não encontrado
    }

    // --- Metodo para excluir
    public static boolean excluirAtividade(int idParaExcluir) {
        ArrayList<Atividade> lista = lerLista(); // 1. Puxa a lista

        for (int i = 0; i < lista.size(); i++) {
            // 2. Procura quem tem o ID
            if (lista.get(i).getId() == idParaExcluir) {
                lista.remove(i); // 3. Remove a atividade da lista
                salvarLista(lista); // 4. Salva a lista mais curta no arquivo
                return true; // Avisa a tela que deu certo
            }
        }
        return false; // ID não encontrado
    }
}