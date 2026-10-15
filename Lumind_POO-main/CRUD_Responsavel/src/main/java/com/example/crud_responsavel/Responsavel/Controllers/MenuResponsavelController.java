package com.example.crud_responsavel.Responsavel.Controllers;

import com.example.crud_responsavel.MenuUsuario;
import com.example.crud_responsavel.SessaoUsuario;
import com.example.crud_responsavel.LoginController;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Evento.Controllers.EventoListaController;
import com.example.crud_responsavel.Relatorio.Controllers.RelatorioListaController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuResponsavelController implements MenuUsuario {
    private Stage stagePrincipal;
    private Scene cena;

    public MenuResponsavelController(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
        this.criarComponentes();
    }

    @Override
    public void mostrar() {
        this.stagePrincipal.setTitle("Painel do Responsável");
        this.stagePrincipal.setScene(this.cena);
        this.stagePrincipal.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label("Painel do Responsável");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblBoasVindas = new Label("Bem-vindo(a), " + SessaoUsuario.getInstancia().getUsuarioLogado().getNome() + "!");

        // --- BOTÕES EXCLUSIVOS DO RESPONSÁVEL ---
        Button btnAbrirRelatorio = new Button("Meus Relatórios");
        btnAbrirRelatorio.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px;");

        Button btnAbrirEvento = new Button("Eventos Cadastrados");
        btnAbrirEvento.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px;");

        Button btnSair = new Button("Sair (Logout)");
        btnSair.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        // --- AÇÕES DOS BOTÕES ---
        btnAbrirRelatorio.setOnAction(e -> {
            RelatorioListaController tela = new RelatorioListaController(this.stagePrincipal);
            tela.mostrar();
        });

        btnAbrirEvento.setOnAction(e -> {
            EventoListaController tela = new EventoListaController(this.stagePrincipal);
            tela.mostrar();
        });

        btnSair.setOnAction(e -> {
            LoginController telaLogin = new LoginController(this.stagePrincipal);
            telaLogin.mostrar();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(lblTitulo, lblBoasVindas, btnAbrirRelatorio, btnAbrirEvento, btnSair);

        // Aumentei a altura para 400
        this.cena = new Scene(layout, 400, 400);
    }
}