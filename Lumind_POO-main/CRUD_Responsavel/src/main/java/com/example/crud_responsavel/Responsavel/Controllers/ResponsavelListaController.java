package com.example.crud_responsavel.Responsavel.Controllers;

import com.example.crud_responsavel.ArquivoUsuario;
import com.example.crud_responsavel.Tipo;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Responsavel.Responsavel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ResponsavelListaController {
    private Stage stage;
    private Scene cena;

    public ResponsavelListaController(Stage stage) {
        this.stage = stage;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle("Lista de Responsáveis");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        // --- CABEÇALHO ---
        HBox cabecalho = new HBox(10);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Responsáveis Cadastrados");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Espaçador para jogar os botões para o canto direito
        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovo = new Button("+ Novo Responsável");
        btnNovo.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar ao Menu");

        cabecalho.getChildren().addAll(titulo, espacador, btnNovo, btnVoltar);

        // --- TABELA ---
        TableView<Responsavel> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Faz as colunas ocuparem todo o espaço

        // Coluna ID
        TableColumn<Responsavel, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        colId.setMaxWidth(50);

        // Coluna Nome
        TableColumn<Responsavel, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        // Coluna Email
        TableColumn<Responsavel, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        // Coluna Ações (O botão de Gerenciar)
        TableColumn<Responsavel, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMaxWidth(100);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnGerenciar = new Button("Gerenciar");
            {
                btnGerenciar.setStyle("-fx-cursor: hand;");
                btnGerenciar.setOnAction(event -> {
                    Responsavel selecionado = getTableView().getItems().get(getIndex());
                    ResponsavelFormController form = new ResponsavelFormController(stage, selecionado);
                    form.mostrar();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(btnGerenciar);
            }
        });

        tabela.getColumns().addAll(colId, colNome, colEmail, colAcoes);

        // --- FILTRO INSTANCEOF AQUI ---
        ArrayList<Usuario> listaGeral = ArquivoUsuario.lerLista();
        for (Usuario u : listaGeral) {
            if (u instanceof Responsavel) {
                tabela.getItems().add((Responsavel) u);
            }
        }

        // --- AÇÕES DOS BOTÕES DE TOPO ---
        btnNovo.setOnAction(e -> {
            ResponsavelFormController form = new ResponsavelFormController(stage, null);
            form.mostrar();
        });

        btnVoltar.setOnAction(e -> {
            new com.example.crud_responsavel.Profissional.Controllers.MenuProfissionalController(stage).mostrar();
        });

        layout.getChildren().addAll(cabecalho, tabela);
        this.cena = new Scene(layout, 650, 450);
    }
}