package com.example.crud_responsavel.Administrador;

import com.example.crud_responsavel.Tipo;
import com.example.crud_responsavel.Usuario;

import java.io.Serializable;
import java.time.LocalDate;

public class Administrador extends Usuario implements Serializable {

    public Administrador(int id, String nome, String email, String senha, String cpf, LocalDate dataNascimento, String telefone, Tipo tipo) {
        super(id, nome, email, senha, cpf, dataNascimento, telefone, tipo);
    }

}