package com.example.crud_responsavel.Evento;

import java.io.*;
import java.util.ArrayList;

public class ArquivoEvento {
    private static final String CAMINHO_ARQUIVO = "Evento.dat";

    public static void salvarLista(ArrayList<Evento> eventos) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq))) {
                oos.writeObject(eventos);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static void adicionarEvento(Evento evento) {
        ArrayList<Evento> eventos = lerLista();
        eventos.add(evento);
        salvarLista(eventos);
    }

    public static ArrayList<Evento> lerLista() {
        ArrayList<Evento> lista = new ArrayList<>();
        File arq = new File(CAMINHO_ARQUIVO);
        if (!arq.exists()) {
            return lista;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            lista = (ArrayList<Evento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    public static boolean atualizarEvento(Evento atualizado) {
        ArrayList<Evento> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                salvarLista(lista);
                return true;
            }
        }
        return false;
    }

    public static boolean excluirEvento(int idParaExcluir) {
        ArrayList<Evento> lista = lerLista();
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