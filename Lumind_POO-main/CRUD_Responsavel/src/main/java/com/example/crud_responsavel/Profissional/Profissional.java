package com.example.crud_responsavel.Profissional;

import com.example.crud_responsavel.Tipo;
import com.example.crud_responsavel.Usuario;

import java.io.Serializable;
import java.time.LocalDate;

public class Profissional extends Usuario implements Serializable {

    private String registroProfissional;
    private String especialidade;
    private String numeroIdentidade;

    public Profissional(int id, String nome, String email, String senha, String cpf, LocalDate dataNascimento, String telefone, Tipo tipo, String registroProfissional, String especialidade, String numeroIdentidade) {
        super(id, nome, email, senha, cpf, dataNascimento, telefone, tipo);
        this.registroProfissional = registroProfissional;
        this.especialidade = especialidade;
        this.numeroIdentidade = numeroIdentidade;
    }



    public String getRegistroProfissional() { return registroProfissional; }
    public void setRegistroProfissional(String registroProfissional) { this.registroProfissional = registroProfissional; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getNumeroIdentidade() { return numeroIdentidade; }
    public void setNumeroIdentidade(String numeroIdentidade) { this.numeroIdentidade = numeroIdentidade; }
}