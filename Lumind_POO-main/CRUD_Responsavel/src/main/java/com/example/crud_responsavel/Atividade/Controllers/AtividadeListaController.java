package com.example.crud_responsavel.Atividade.Controllers;

import com.example.crud_responsavel.Observador;
import com.example.crud_responsavel.Profissional.Controllers.MenuProfissionalController;
import com.example.crud_responsavel.Atividade.Atividade;
import com.example.crud_responsavel.Atividade.ArquivoAtividade;
import com.example.crud_responsavel.Atividade.HistoricoAtividade;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

// OBSERVER: esta tela é um "observador" e será avisada quando as atividades mudarem
public class AtividadeListaController implements Observador {
    private Stage stage;
    private Scene cena;
    private TableView<Atividade> tabela;

    public AtividadeListaController(Stage stage) {
        this.stage = stage;
        this.criarComponentes();

        // OBSERVER: se inscreve para ser avisada quando os dados forem salvos
        ArquivoAtividade.adicionarObservador(this);
    }

    // OBSERVER: chamado automaticamente pelo ArquivoAtividade após cada gravação
    @Override
    public void atualizar() {
        recarregarTabela();
    }

    private void recarregarTabela() {
        tabela.getItems().setAll(ArquivoAtividade.lerLista());
    }

    // OBSERVER: cancela a inscrição ao sair da tela
    private void sairDaTela() {
        ArquivoAtividade.removerObservador(this);
    }

    public void mostrar() {
        this.stage.setTitle("Lista de Atividades");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        HBox cabecalho = new HBox(10);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Atividades Cadastradas");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovo = new Button("+ Nova Atividade");
        btnNovo.setStyle("-fx-background-color: #0d6efd; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar ao Menu");

        // MEMENTO: desfaz a última alteração ou exclusão de atividade
        Button btnDesfazer = new Button("Desfazer");
        btnDesfazer.setDisable(!HistoricoAtividade.getInstancia().podeDesfazer());
        btnDesfazer.setOnAction(e -> {
            HistoricoAtividade historico = HistoricoAtividade.getInstancia();
            historico.desfazer(); // grava no arquivo e o Observer recarrega a tabela
            btnDesfazer.setDisable(!historico.podeDesfazer());
        });

        cabecalho.getChildren().addAll(titulo, espacador, btnDesfazer, btnNovo, btnVoltar);

        tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Atividade, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        colId.setMaxWidth(50);

        TableColumn<Atividade, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));

        TableColumn<Atividade, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoria()));

        TableColumn<Atividade, LocalDate> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getData()));

        TableColumn<Atividade, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setMaxWidth(100);
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnGerenciar = new Button("Gerenciar");
            {
                btnGerenciar.setStyle("-fx-cursor: hand;");
                btnGerenciar.setOnAction(event -> {
                    sairDaTela();
                    Atividade selecionada = getTableView().getItems().get(getIndex());
                    AtividadeFormController form = new AtividadeFormController(stage, selecionada);
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

        tabela.getColumns().addAll(colId, colTitulo, colCategoria, colData, colAcoes);

        // Carrega todas as atividades do arquivo direto para a tabela
        recarregarTabela();

        btnNovo.setOnAction(e -> {
            sairDaTela();
            AtividadeFormController form = new AtividadeFormController(stage, null);
            form.mostrar();
        });

        btnVoltar.setOnAction(e -> {
            sairDaTela();
            new MenuProfissionalController(stage).mostrar();
        });

        layout.getChildren().addAll(cabecalho, tabela);
        this.cena = new Scene(layout, 650, 450);
    }
}