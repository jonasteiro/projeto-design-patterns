package com.example.crud_responsavel.Relatorio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe que representa um Relatório do sistema.
 * Implementa Serializable para poder ser salva/lida de arquivo.
 * Segue exatamente o mesmo padrão da classe Atividade.
 */
public class Relatorio implements Serializable {

    // Identificador único do relatório (tipo INT)
    private int id;

    // ID da pessoa com TEA vinculada ao relatório (tipo INT)
    private int idPessoaTea;

    // ID do responsável vinculado ao relatório (tipo INT)
    private int idResponsavel;

    // Descrição detalhada do relatório (tipo TEXT)
    private String descricao;

    // Data do relatório (tipo DATE)
    private LocalDate data;

    // Construtor: cria um relatório já com todos os dados preenchidos
    public Relatorio(int id, int idPessoaTea, int idResponsavel, String descricao, LocalDate data) {
        this.id = id;
        this.idPessoaTea = idPessoaTea;
        this.idResponsavel = idResponsavel;
        this.descricao = descricao;
        this.data = data;
    }

    // --- GETTERS E SETTERS ---

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdPessoaTea() {
        return idPessoaTea;
    }
    public void setIdPessoaTea(int idPessoaTea) {
        this.idPessoaTea = idPessoaTea;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }
    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
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

    /**
     * Descobre o próximo ID livre para usar num novo cadastro.
     * Percorre a lista de relatórios já cadastrados e retorna
     * o primeiro número inteiro positivo que ainda não está em uso.
     */
    public static int descobrirIdLivre(ArrayList<Relatorio> listaCadastrados) {
        int idTentativa = 1;
        boolean idLivre = false;

        while (!idLivre) {
            boolean idJaExiste = false;

            // Percorre toda a lista verificando se o ID tentativa já existe
            for (Relatorio r : listaCadastrados) {
                if (r.getId() == idTentativa) {
                    idJaExiste = true;
                    break;
                }
            }

            if (idJaExiste) {
                idTentativa++; // esse ID já está ocupado, tenta o próximo
            } else {
                idLivre = true; // encontrou um ID livre, sai do loop
            }
        }

        return idTentativa;
    }
}