package com.example.crud_responsavel.Profissional.Controllers;

import com.example.crud_responsavel.MenuUsuario;
import com.example.crud_responsavel.SessaoUsuario;
import com.example.crud_responsavel.LoginController;
import com.example.crud_responsavel.PessoaTea.Controllers.PessoaTeaListaController;
import com.example.crud_responsavel.Responsavel.Controllers.ResponsavelListaController;
import com.example.crud_responsavel.Atividade.Controllers.AtividadeListaController;
import com.example.crud_responsavel.Feedback.Controllers.FeedbackListaController;
import com.example.crud_responsavel.Usuario;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuProfissionalController implements MenuUsuario {
    private Stage stagePrincipal;
    private Scene cena;

    public MenuProfissionalController(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
        this.criarComponentes();
    }

    @Override
    public void mostrar() {
        this.stagePrincipal.setTitle("Painel Clínico");
        this.stagePrincipal.setScene(this.cena);
        this.stagePrincipal.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label("Painel do Profissional");
        lblTitulo.setFont(new Font("Arial", 22));

        Label lblBoasVindas = new Label("Profissional: " + SessaoUsuario.getInstancia().getUsuarioLogado().getNome());
        lblBoasVindas.setStyle("-fx-font-style: italic; -fx-text-fill: gray;");

        // --- BOTÕES EXCLUSIVOS DO PROFISSIONAL ---
        Button btnAbrirResponsavel = new Button("Gerenciar Responsáveis");
        btnAbrirResponsavel.setFont(new Font("Arial", 14));
        btnAbrirResponsavel.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-padding: 10px 20px;");

        Button btnAbrirPessoaTea = new Button("Gerenciar Pacientes TEA");
        btnAbrirPessoaTea.setFont(new Font("Arial", 14));
        btnAbrirPessoaTea.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-padding: 10px 20px;");

        Button btnAbrirAtividade = new Button("Gerenciar Atividades");
        btnAbrirAtividade.setFont(new Font("Arial", 14));
        btnAbrirAtividade.setStyle("-fx-background-color: #6f42c1; -fx-text-fill: white; -fx-padding: 10px 20px;");

        Button btnAbrirFeedback = new Button("Gerenciar Feedbacks");
        btnAbrirFeedback.setFont(new Font("Arial", 14));
        btnAbrirFeedback.setStyle("-fx-background-color: #fd7e14; -fx-text-fill: white; -fx-padding: 10px 20px;");

        Button btnSair = new Button("Sair (Logout)");
        btnSair.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        // --- AÇÕES DOS BOTÕES ---
        btnAbrirResponsavel.setOnAction(event -> {
            ResponsavelListaController tela = new ResponsavelListaController(this.stagePrincipal);
            tela.mostrar();
        });

        btnAbrirPessoaTea.setOnAction(event -> {
            PessoaTeaListaController tela = new PessoaTeaListaController(this.stagePrincipal);
            tela.mostrar();
        });

        btnAbrirAtividade.setOnAction(event -> {
            AtividadeListaController tela = new AtividadeListaController(this.stagePrincipal);
            tela.mostrar();
        });

        btnAbrirFeedback.setOnAction(event -> {
            FeedbackListaController tela = new FeedbackListaController(this.stagePrincipal);
            tela.mostrar();
        });

        btnSair.setOnAction(event -> {
            LoginController telaLogin = new LoginController(this.stagePrincipal);
            telaLogin.mostrar();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(lblTitulo, lblBoasVindas, btnAbrirResponsavel, btnAbrirPessoaTea, btnAbrirAtividade, btnAbrirFeedback, btnSair);

        // Aumentei a altura para 500 para caber todos os botões
        this.cena = new Scene(layout, 450, 500);
    }
}