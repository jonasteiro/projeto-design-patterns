package com.example.crud_responsavel.Feedback.Controllers;

import com.example.crud_responsavel.Profissional.Controllers.MenuProfissionalController;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Feedback.Feedback;
import com.example.crud_responsavel.Feedback.ArquivoFeedback;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class FeedbackListaController {
    private Stage stage;
    private Scene cena;

    public FeedbackListaController(Stage stage) {
        this.stage = stage;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle("Lista de Feedbacks");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        HBox cabecalho = new HBox(10);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Feedbacks Cadastrados");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovo = new Button("+ Novo Feedback");
        btnNovo.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar ao Menu");

        cabecalho.getChildren().addAll(titulo, espacador, btnNovo, btnVoltar);

        TableView<Feedback> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Feedback, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        colId.setMaxWidth(50);

        TableColumn<Feedback, String> colProfissional = new TableColumn<>("Profissional");
        colProfissional.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomeProfissional()));

        TableColumn<Feedback, LocalDate> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDataAvaliacao()));

        TableColumn<Feedback, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMaxWidth(100);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnGerenciar = new Button("Gerenciar");
            {
                btnGerenciar.setStyle("-fx-cursor: hand;");
                btnGerenciar.setOnAction(event -> {
                    Feedback selecionado = getTableView().getItems().get(getIndex());
                    FeedbackFormController form = new FeedbackFormController(stage, selecionado);
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

        tabela.getColumns().addAll(colId, colProfissional, colData, colAcoes);

        ArrayList<Feedback> listaGeral = ArquivoFeedback.lerLista();
        tabela.getItems().addAll(listaGeral);

        btnNovo.setOnAction(e -> {
            FeedbackFormController form = new FeedbackFormController(stage, null);
            form.mostrar();
        });

        btnVoltar.setOnAction(e -> {
            new MenuProfissionalController(stage).mostrar();
        });

        layout.getChildren().addAll(cabecalho, tabela);
        this.cena = new Scene(layout, 650, 450);
    }
}