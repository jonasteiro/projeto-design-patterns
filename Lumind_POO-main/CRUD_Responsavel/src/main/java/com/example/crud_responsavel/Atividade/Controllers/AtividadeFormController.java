package com.example.crud_responsavel.Atividade.Controllers;

import com.example.crud_responsavel.Atividade.Atividade;
import com.example.crud_responsavel.Atividade.ArquivoAtividade;
import com.example.crud_responsavel.Atividade.HistoricoAtividade;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AtividadeFormController {
    private Stage stage;
    private Scene cena;
    private Atividade atividadeSelecionada;

    public AtividadeFormController(Stage stage, Atividade atividadeSelecionada) {
        this.stage = stage;
        this.atividadeSelecionada = atividadeSelecionada;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle(atividadeSelecionada == null ? "Nova Atividade" : "Gerenciar Atividade");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label(atividadeSelecionada == null ? "Cadastrar Atividade" : "Alterar / Excluir Atividade");
        lblTitulo.setFont(new Font("Arial", 20));

        Label lblTituloAtiv = new Label("Título:");
        TextField txtTitulo = new TextField();

        Label lblCategoria = new Label("Categoria:");
        TextField txtCategoria = new TextField();
        txtCategoria.setPromptText("Ex: Educativa, Esportiva...");

        Label lblData = new Label("Data:");
        DatePicker dpData = new DatePicker();

        Label lblDescricao = new Label("Descrição:");
        TextArea txtDescricao = new TextArea();
        txtDescricao.setPrefRowCount(4);

        if (atividadeSelecionada != null) {
            txtTitulo.setText(atividadeSelecionada.getTitulo());
            txtCategoria.setText(atividadeSelecionada.getCategoria());
            dpData.setValue(atividadeSelecionada.getData());
            txtDescricao.setText(atividadeSelecionada.getDescricao());
        }

        GridPane grade = new GridPane();
        grade.setVgap(10); grade.setHgap(10); grade.setAlignment(Pos.CENTER);
        grade.add(lblTituloAtiv, 0, 0); grade.add(txtTitulo, 1, 0);
        grade.add(lblCategoria, 0, 1); grade.add(txtCategoria, 1, 1);
        grade.add(lblData, 0, 2); grade.add(dpData, 1, 2);
        grade.add(lblDescricao, 0, 3); grade.add(txtDescricao, 1, 3);

        Button btnSalvar = new Button(atividadeSelecionada == null ? "Cadastrar" : "Salvar Alterações");
        btnSalvar.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnExcluir.setVisible(atividadeSelecionada != null);

        Button btnCancelar = new Button("Cancelar e Voltar");
        Label lblResultado = new Label();

        btnSalvar.setOnAction(e -> {
            String titulo = txtTitulo.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String descricao = txtDescricao.getText().trim();

            // 1. Validação: Título, Data, Categoria e Descrição são obrigatórios
            if (titulo.isEmpty() || dpData.getValue() == null || categoria.isEmpty() || descricao.isEmpty()) {
                lblResultado.setText("Erro: Preencha todos os campos, incluindo Data!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            // 2. Validação: A descrição deve ter um tamanho mínimo (ex: 10 caracteres)
            if (descricao.length() < 10) {
                lblResultado.setText("Erro: A descrição deve ter pelo menos 10 caracteres.");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            if (atividadeSelecionada == null) {
                ArrayList<Atividade> todas = ArquivoAtividade.lerLista();
                int novoId = Atividade.descobrirIdLivre(todas);

                Atividade nova = new Atividade(novoId, txtTitulo.getText(), dpData.getValue(), txtDescricao.getText(), txtCategoria.getText());
                ArquivoAtividade.adicionarAtividade(nova);
            } else {
                // MEMENTO: guarda uma "foto" da atividade antes de alterar
                HistoricoAtividade.getInstancia().guardarAntesDeAlterar(atividadeSelecionada);

                Atividade atualizada = new Atividade(atividadeSelecionada.getId(), txtTitulo.getText(), dpData.getValue(), txtDescricao.getText(), txtCategoria.getText());
                ArquivoAtividade.atualizarAtividade(atualizada);
            }

            AtividadeListaController telaLista = new AtividadeListaController(stage);
            telaLista.mostrar();
        });

        btnExcluir.setOnAction(e -> {
            // MEMENTO: guarda uma "foto" da atividade antes de excluir
            HistoricoAtividade.getInstancia().guardarAntesDeExcluir(atividadeSelecionada);

            ArquivoAtividade.excluirAtividade(atividadeSelecionada.getId());
            AtividadeListaController telaLista = new AtividadeListaController(stage);
            telaLista.mostrar();
        });

        btnCancelar.setOnAction(e -> {
            AtividadeListaController telaLista = new AtividadeListaController(stage);
            telaLista.mostrar();
        });

        HBox botoes = new HBox(15, btnSalvar, btnExcluir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, lblTitulo, grade, botoes, lblResultado);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.cena = new Scene(layout, 500, 450);
    }
}