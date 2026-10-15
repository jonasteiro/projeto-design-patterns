package com.example.crud_responsavel.PessoaTea.Controllers;

import com.example.crud_responsavel.MenuUsuario;
import com.example.crud_responsavel.SessaoUsuario;
import com.example.crud_responsavel.LoginController;
import com.example.crud_responsavel.Usuario;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPessoaTeaController implements MenuUsuario {
    private Stage stagePrincipal;
    private Scene cena;

    public MenuPessoaTeaController(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
        this.criarComponentes();
    }

    @Override
    public void mostrar() {
        this.stagePrincipal.setTitle("Área do Paciente");
        this.stagePrincipal.setScene(this.cena);
        this.stagePrincipal.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label("Área do Paciente TEA");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblBoasVindas = new Label("Olá, " + SessaoUsuario.getInstancia().getUsuarioLogado().getNome() + "!");

        Label lblInstrucao = new Label("Aqui aparecerão suas atividades e feedbacks em breve.");
        lblInstrucao.setStyle("-fx-text-fill: #555; -fx-font-style: italic;");

        Button btnSair = new Button("Sair do Sistema");
        btnSair.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");

        btnSair.setOnAction(e -> new LoginController(stagePrincipal).mostrar());

        VBox layout = new VBox(20, lblTitulo, lblBoasVindas, lblInstrucao, btnSair);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        this.cena = new Scene(layout, 400, 350);
    }
}