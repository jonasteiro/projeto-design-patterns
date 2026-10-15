package com.example.crud_responsavel;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LoginController {
    private Stage stage;
    private Scene cena;

    public LoginController(Stage stage) {
        this.stage = stage;
        this.criarComponentes();
    }

    public void mostrar() {
        // Chegar na tela de login significa que não há ninguém logado
        SessaoUsuario.getInstancia().encerrar();
        this.stage.setTitle("Login - Sistema CMSC");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label("Acesso ao Sistema");
        lblTitulo.setFont(new Font("Arial", 24));

        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField();
        txtEmail.setMaxWidth(250);

        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setMaxWidth(250);

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-font-weight: bold;");

        Label lblMensagem = new Label();
        lblMensagem.setStyle("-fx-text-fill: red;");


        //------------
        //Pega o valor digitado e compara com o usuario no arquivo .dat
        btnEntrar.setOnAction(e -> {
            String emailDigitado = txtEmail.getText();
            String senhaDigitada = txtSenha.getText();

            // Busca o usuário no arquivo unificado
            ArrayList<Usuario> lista = ArquivoUsuario.lerLista();
            Usuario usuarioLogado = null;

            for (Usuario u : lista) {
                if (u.getEmail().equals(emailDigitado) && u.getSenha().equals(senhaDigitada)) {
                    usuarioLogado = u;
                    break;
                }
            }
            //-----------

            if (usuarioLogado != null) {
                // Guarda o usuário na sessão (Singleton)
                SessaoUsuario.getInstancia().iniciar(usuarioLogado);

                // Abre o menu correto para o tipo do usuário (Factory Method)
                MenuCreator.para(usuarioLogado.getTipo()).abrirMenu(this.stage);
            } else {
                lblMensagem.setText("E-mail ou senha incorretos!");
                lblMensagem.setStyle("-fx-text-fill: red;");
            }
        });

        VBox layout = new VBox(15, lblTitulo, lblEmail, txtEmail, lblSenha, txtSenha, btnEntrar, lblMensagem);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        this.cena = new Scene(layout, 400, 400);
    }
}