package com.example.crud_responsavel.Administrador.Controllers;

import com.example.crud_responsavel.MenuUsuario;
import com.example.crud_responsavel.SessaoUsuario;
import com.example.crud_responsavel.LoginController;
import com.example.crud_responsavel.Profissional.Controllers.ProfissionalListaController;
import com.example.crud_responsavel.Usuario;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuAdministradorController implements MenuUsuario {
    private Stage stagePrincipal;
    private Scene cena;

    public MenuAdministradorController(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
        this.criarComponentes();
    }

    @Override
    public void mostrar() {
        this.stagePrincipal.setTitle("Painel do Administrador");
        this.stagePrincipal.setScene(this.cena);
        this.stagePrincipal.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label("Painel Administrativo");
        lblTitulo.setFont(new Font("Arial", 22));

        Label lblBoasVindas = new Label("Olá, " + SessaoUsuario.getInstancia().getUsuarioLogado().getNome() + "!");
        lblBoasVindas.setStyle("-fx-font-style: italic; -fx-text-fill: gray;");

        // --- BOTÕES ESPECÍFICOS DESTE PERFIL ---
        Button btnAbrirAdmin = new Button("Gerenciar Administradores do Sistema");
        btnAbrirAdmin.setFont(new Font("Arial", 14));
        btnAbrirAdmin.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-padding: 10px 20px;");

        Button btnGerenciarProfissional = new Button("Gerenciar Profissionais de Saúde");
        btnGerenciarProfissional.setOnAction(e -> {
            // Chama a tela de lista de profissionais passando o stage atual
            ProfissionalListaController tela = new ProfissionalListaController(this.stagePrincipal);
            tela.mostrar();
        });

        Button btnSair = new Button("Sair (Logout)");
        btnSair.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        // --- AÇÕES ---
        btnAbrirAdmin.setOnAction(event -> {
            // Abre o CRUD de Admin (Lista)
            AdministradorListaController telaTabela = new AdministradorListaController(this.stagePrincipal);
            telaTabela.mostrar();
        });

        btnSair.setOnAction(event -> {
            // Volta para o Login, matando a sessão
            LoginController telaLogin = new LoginController(this.stagePrincipal);
            telaLogin.mostrar();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(lblTitulo, lblBoasVindas, btnAbrirAdmin, btnGerenciarProfissional,btnSair);

        this.cena = new Scene(layout, 500, 400);
    }
}