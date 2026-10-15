package com.example.crud_responsavel;
import java.io.Serializable;
import java.time.LocalDate;


public class Usuario implements Serializable {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private Tipo tipo;

    public Usuario(int id, String nome, String email, String senha, String cpf, LocalDate dataNascimento, String telefone, Tipo tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    //java.util.ArrayList<? extends Usuari
    //metodo funciona para qualquer lista, contanto que os itens dentro dela sejam do tipo Usuario
    // ou qualquer classe que herde de Usuario
    public static int descobrirIdLivre(java.util.ArrayList<? extends Usuario> listaCadastrados) {
        // Busca pelo ID 1
        int idTentativa = 1;
        boolean idLivre = false; //controla o loop

        while (!idLivre) {
            boolean idJaExiste = false;

            // Percorre toda a lista de usuários existentes para comparar com o ID atual
            for (Usuario u : listaCadastrados) {
                if (u.getId() == idTentativa) {
                    idJaExiste = true; //Se o id da lista for igual ao de teste > não livre
                    break;
                }
            }

            // Se o ID já existia na lista, passa para o próximo número
            if (idJaExiste) {
                idTentativa++;
            } else {
                // Se o loop terminou e não encontrou ID, então ele está livre
                idLivre = true;
            }
        }

        // Retorna o ID que validou como livre
        return idTentativa;
    }

}
