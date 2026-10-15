package com.example.crud_responsavel.Evento.Controllers;

// Ajuste o import do menu do Responsável se o caminho for diferente no seu projeto
import com.example.crud_responsavel.Responsavel.Controllers.MenuResponsavelController;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Evento.Evento;
import com.example.crud_responsavel.Evento.ArquivoEvento;

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

public class EventoListaController {
    private Stage stage;
    private Scene cena;

    public EventoListaController(Stage stage) {
        this.stage = stage;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle("Lista de Eventos");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        HBox cabecalho = new HBox(10);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Eventos Cadastrados");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovo = new Button("+ Novo Evento");
        btnNovo.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar ao Menu");

        cabecalho.getChildren().addAll(titulo, espacador, btnNovo, btnVoltar);

        TableView<Evento> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Evento, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        colId.setMaxWidth(50);

        TableColumn<Evento, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));

        TableColumn<Evento, LocalDate> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getData()));

        TableColumn<Evento, String> colLocal = new TableColumn<>("Local");
        colLocal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocal()));

        TableColumn<Evento, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMaxWidth(100);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnGerenciar = new Button("Gerenciar");
            {
                btnGerenciar.setStyle("-fx-cursor: hand;");
                btnGerenciar.setOnAction(event -> {
                    Evento selecionado = getTableView().getItems().get(getIndex());
                    EventoFormController form = new EventoFormController(stage, selecionado);
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

        tabela.getColumns().addAll(colId, colTitulo, colData, colLocal, colAcoes);

        // Carrega a lista inteira do arquivo para a tabela
        ArrayList<Evento> listaGeral = ArquivoEvento.lerLista();
        tabela.getItems().addAll(listaGeral);

        btnNovo.setOnAction(e -> {
            EventoFormController form = new EventoFormController(stage, null);
            form.mostrar();
        });

        btnVoltar.setOnAction(e -> {
            // Volta para o menu do Responsável passando o bastão
            new MenuResponsavelController(stage).mostrar();
        });

        layout.getChildren().addAll(cabecalho, tabela);
        this.cena = new Scene(layout, 650, 450);
    }
}