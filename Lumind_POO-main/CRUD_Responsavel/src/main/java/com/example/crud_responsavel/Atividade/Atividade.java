package com.example.crud_responsavel.Atividade;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe que representa uma Atividade do sistema.
 * Diferente do Responsavel, a Atividade NÃO é um tipo de Usuario,
 * por isso ela não estende a classe Usuario: ela implementa Serializable
 * diretamente, só pra poder ser salva/lida de arquivo (igual o Usuario faz).
 */
public class Atividade implements Serializable {

    // Mantém compatibilidade com o arquivo Atividade.dat já salvo
    private static final long serialVersionUID = -5467296328993793980L;

    // Identificador único da atividade (tipo INT)
    private int id;

    // Título da atividade (tipo VARCHAR)
    private String titulo;

    // Data da atividade (tipo DATE)
    private LocalDate data;

    // Descrição detalhada da atividade (tipo TEXT)
    private String descricao;

    // Categoria da atividade, ex: "Esportiva", "Educativa" (tipo VARCHAR)
    private String categoria;

    // Construtor: cria uma atividade já com todos os dados preenchidos
    public Atividade(int id, String titulo, LocalDate data, String descricao, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    // --- GETTERS E SETTERS ---
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

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Descobre o próximo ID livre pra usar num novo cadastro.
     * É o equivalente do Usuario.descobrirIdLivre(), mas mora aqui dentro
     * da própria classe Atividade porque ela não estende Usuario.
     */
    public static int descobrirIdLivre(java.util.ArrayList<Atividade> listaCadastradas) {
        int idTentativa = 1;
        boolean idLivre = false;

        while (!idLivre) {
            boolean idJaExiste = false;

            for (Atividade a : listaCadastradas) {
                if (a.getId() == idTentativa) {
                    idJaExiste = true;
                    break;
                }
            }

            if (idJaExiste) {
                idTentativa++; // tenta o próximo número
            } else {
                idLivre = true; // achou um ID livre, sai do loop
            }
        }

        return idTentativa;
    }

    // ===================== PADRÃO MEMENTO =====================

    /**
     * Tira uma "foto" do estado atual desta atividade.
     * A foto (Memento) é imutável e guarda todos os dados.
     */
    public Memento salvarEstado() {
        return new Memento(id, titulo, data, descricao, categoria);
    }

    /**
     * Cria uma Atividade a partir de uma "foto" tirada antes.
     */
    public static Atividade restaurarDe(Memento memento) {
        return new Atividade(memento.id, memento.titulo, memento.data,
                memento.descricao, memento.categoria);
    }

    /**
     * O Memento. Os campos são privados: só a classe Atividade consegue ler.
     * Quem guarda o memento (HistoricoAtividade) não enxerga o conteúdo.
     */
    public static final class Memento {
        private final int id;
        private final String titulo;
        private final LocalDate data;
        private final String descricao;
        private final String categoria;

        private Memento(int id, String titulo, LocalDate data, String descricao, String categoria) {
            this.id = id;
            this.titulo = titulo;
            this.data = data;
            this.descricao = descricao;
            this.categoria = categoria;
        }
    }
}
