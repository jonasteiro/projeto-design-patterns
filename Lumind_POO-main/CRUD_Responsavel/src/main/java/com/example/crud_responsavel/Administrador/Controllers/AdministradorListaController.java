package com.example.crud_responsavel.Administrador.Controllers;

//import com.example.crud_responsavel.MainController;
import com.example.crud_responsavel.ArquivoUsuario;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Administrador.Administrador;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AdministradorListaController {
    private Stage stage;
    private Scene cena;

    public AdministradorListaController(Stage stage) {
        this.stage = stage;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle("Lista de Administradores");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        HBox cabecalho = new HBox(10);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Equipe de Administração");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovo = new Button("+ Novo Admin");
        btnNovo.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar ao Menu");

        cabecalho.getChildren().addAll(titulo, espacador, btnNovo, btnVoltar);

        TableView<Administrador> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Administrador, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        colId.setMaxWidth(50);

        TableColumn<Administrador, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Administrador, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Administrador, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMaxWidth(100);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnGerenciar = new Button("Gerenciar");
            {
                btnGerenciar.setStyle("-fx-cursor: hand;");
                btnGerenciar.setOnAction(event -> {
                    Administrador selecionado = getTableView().getItems().get(getIndex());
                    AdministradorFormController form = new AdministradorFormController(stage, selecionado);
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

        // --- MUDANÇA AQUI: Filtro com instanceof ---
        ArrayList<Usuario> listaGeral = ArquivoUsuario.lerLista();
        for (Usuario u : listaGeral) {
            if (u instanceof Administrador) {
                tabela.getItems().add((Administrador) u);
            }
        }

        btnNovo.setOnAction(e -> {
            AdministradorFormController form = new AdministradorFormController(stage, null);
            form.mostrar();
        });

        btnVoltar.setOnAction(e -> {
            new com.example.crud_responsavel.Administrador.Controllers.MenuAdministradorController(stage).mostrar();
        });

        layout.getChildren().addAll(cabecalho, tabela);
        this.cena = new Scene(layout, 650, 450);
    }
}