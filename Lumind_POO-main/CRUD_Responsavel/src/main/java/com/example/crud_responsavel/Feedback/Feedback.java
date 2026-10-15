package com.example.crud_responsavel.Feedback;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Feedback implements Serializable {
    private int id;
    private String nomeProfissional;
    private String observacoes;
    private LocalDate dataAvaliacao;

    public Feedback(int id, String nomeProfissional, String observacoes, LocalDate dataAvaliacao) {
        this.id = id;
        this.nomeProfissional = nomeProfissional;
        this.observacoes = observacoes;
        this.dataAvaliacao = dataAvaliacao;
    }

    // --- GETTERS E SETTERS ---
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }
    public void setNomeProfissional(String nomeProfissional) {
        this.nomeProfissional = nomeProfissional;
    }

    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }
    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public static int descobrirIdLivre(ArrayList<Feedback> listaCadastrados) {
        int idTentativa = 1;
        boolean idLivre = false;
        while (!idLivre) {
            boolean idJaExiste = false;
            for (Feedback f : listaCadastrados) {
                if (f.getId() == idTentativa) {
                    idJaExiste = true;
                    break;
                }
            }
            if (idJaExiste) idTentativa++;
            else idLivre = true;
        }
        return idTentativa;
    }
}