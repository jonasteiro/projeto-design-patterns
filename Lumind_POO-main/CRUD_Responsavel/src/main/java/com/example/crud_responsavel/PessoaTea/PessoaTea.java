package com.example.crud_responsavel.PessoaTea;

import com.example.crud_responsavel.Tipo;
import com.example.crud_responsavel.Usuario;

import java.io.Serializable;
import java.time.LocalDate;

public class PessoaTea extends Usuario implements Serializable {
    private int nivelTea;
    private String observacao;

    public PessoaTea(int id, String nome, String email, String senha, String cpf, LocalDate dataNascimento, String telefone, Tipo tipo, int nivelTea, String observacao) {
        super(id, nome, email, senha, cpf, dataNascimento, telefone, tipo);
        this.nivelTea = nivelTea;
        this.observacao = observacao;
    }

    public int getNivelTea() { return nivelTea; }
    public void setNivelTea(int nivelTea) { this.nivelTea = nivelTea; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}