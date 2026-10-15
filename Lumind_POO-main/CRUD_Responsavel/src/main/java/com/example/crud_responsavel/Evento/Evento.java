package com.example.crud_responsavel.Evento;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe que representa um Evento do sistema.
 *
 */

public class Evento implements Serializable {

    private int id;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private String local;

    public Evento(int id, String titulo, String descricao, LocalDate data, String local) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public static int descobrirIdLivre(java.util.ArrayList<Evento> listaCadastrados) {
        int idTentativa = 1;
        boolean idLivre = false;
        while (!idLivre) {
            boolean idJaExiste = false;
            for (Evento e : listaCadastrados) {
                if (e.getId() == idTentativa) {
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
