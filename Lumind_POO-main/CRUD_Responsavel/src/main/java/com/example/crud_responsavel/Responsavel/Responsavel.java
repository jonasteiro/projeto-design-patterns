package com.example.crud_responsavel.Responsavel;

import com.example.crud_responsavel.Tipo;
import com.example.crud_responsavel.Usuario;

import java.time.LocalDate;

public class Responsavel extends Usuario {

    public Responsavel(int id,
                       String nome,
                       String email,
                       String senha,
                       String cpf,
                       LocalDate dataNascimento,
                       String telefone,
                       Tipo tipo){
        super(id, nome, email, senha, cpf, dataNascimento, telefone, tipo);
    }


}
