package com.example.crud_responsavel.Feedback;


import java.io.*;
import java.util.ArrayList;

public class ArquivoFeedback {
    private static final String CAMINHO_ARQUIVO = "Feedback.dat";

    public static void salvarLista(ArrayList<Feedback> feedbacks) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq))) {
                oos.writeObject(feedbacks);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static void adicionarFeedback(Feedback feedback) {
        ArrayList<Feedback> feedbacks = lerLista();
        feedbacks.add(feedback);
        salvarLista(feedbacks);
    }

    public static ArrayList<Feedback> lerLista() {
        ArrayList<Feedback> lista = new ArrayList<>();
        File arq = new File(CAMINHO_ARQUIVO);

        // Se o arquivo for deletado, retorna a lista vazia sem dar crash
        if (!arq.exists()) {
            return lista;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            lista = (ArrayList<Feedback>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    public static boolean atualizarFeedback(Feedback atualizado) {
        ArrayList<Feedback> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                salvarLista(lista);
                return true;
            }
        }
        return false;
    }

    public static boolean excluirFeedback(int idParaExcluir) {
        ArrayList<Feedback> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == idParaExcluir) {
                lista.remove(i);
                salvarLista(lista);
                return true;
            }
        }
        return false;
    }
}