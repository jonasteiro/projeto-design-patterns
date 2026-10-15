package com.example.crud_responsavel.Relatorio.Controllers;

// Ajuste os imports abaixo para o pacote correto do seu MenuResponsavelController e Usuario
import com.example.crud_responsavel.SessaoUsuario;
import com.example.crud_responsavel.Responsavel.Controllers.MenuResponsavelController;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Relatorio.Relatorio;
import com.example.crud_responsavel.Relatorio.ArquivoRelatorio;

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

public class RelatorioListaController {
    private Stage stage;
    private Scene cena;

    public RelatorioListaController(Stage stage) {
        this.stage = stage;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle("Lista de Relatórios");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        HBox cabecalho = new HBox(10);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Meus Relatórios");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovo = new Button("+ Novo Relatório");
        btnNovo.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar ao Menu");

        cabecalho.getChildren().addAll(titulo, espacador, btnNovo, btnVoltar);

        TableView<Relatorio> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Relatorio, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        colId.setMaxWidth(50);

        TableColumn<Relatorio, Integer> colPaciente = new TableColumn<>("ID Paciente TEA");
        colPaciente.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getIdPessoaTea()));

        TableColumn<Relatorio, LocalDate> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getData()));

        TableColumn<Relatorio, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescricao()));

        TableColumn<Relatorio, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMaxWidth(100);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnGerenciar = new Button("Gerenciar");
            {
                btnGerenciar.setStyle("-fx-cursor: hand;");
                btnGerenciar.setOnAction(event -> {
                    Relatorio selecionado = getTableView().getItems().get(getIndex());
                    RelatorioFormController form = new RelatorioFormController(stage, selecionado);
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

        tabela.getColumns().addAll(colId, colPaciente, colData, colDescricao, colAcoes);

        // Carrega a lista do arquivo
        ArrayList<Relatorio> listaGeral = ArquivoRelatorio.lerLista();

        // Filtra para mostrar APENAS os relatórios criados por este responsável logado
        for (Relatorio r : listaGeral) {
            if (r.getIdResponsavel() == SessaoUsuario.getInstancia().getUsuarioLogado().getId()) {
                tabela.getItems().add(r);
            }
        }

        btnNovo.setOnAction(e -> {
            RelatorioFormController form = new RelatorioFormController(stage, null);
            form.mostrar();
        });

        btnVoltar.setOnAction(e -> {
            new MenuResponsavelController(stage).mostrar(); // Volta para o menu do Responsável
        });

        layout.getChildren().addAll(cabecalho, tabela);
        this.cena = new Scene(layout, 650, 450);
    }
}