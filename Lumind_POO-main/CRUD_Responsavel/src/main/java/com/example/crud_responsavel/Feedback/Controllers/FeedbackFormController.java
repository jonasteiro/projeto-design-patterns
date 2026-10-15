package com.example.crud_responsavel.Feedback.Controllers;

import com.example.crud_responsavel.SessaoUsuario;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Feedback.Feedback;
import com.example.crud_responsavel.Feedback.ArquivoFeedback;

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

public class FeedbackFormController {
    private Stage stage;
    private Scene cena;
    private Feedback feedbackSelecionado;

    public FeedbackFormController(Stage stage, Feedback feedbackSelecionado) {
        this.stage = stage;
        this.feedbackSelecionado = feedbackSelecionado;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle(feedbackSelecionado == null ? "Novo Feedback" : "Gerenciar Feedback");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label(feedbackSelecionado == null ? "Cadastrar Feedback" : "Alterar / Excluir Feedback");
        lblTitulo.setFont(new Font("Arial", 20));

        Label lblProfissional = new Label("Profissional:");
        TextField txtProfissional = new TextField();

        if (feedbackSelecionado == null) {
            txtProfissional.setText(SessaoUsuario.getInstancia().getUsuarioLogado().getNome());
        }

        Label lblData = new Label("Data da Avaliação:");
        DatePicker dpData = new DatePicker();

        Label lblObservacoes = new Label("Observações:");
        TextArea txtObservacoes = new TextArea();
        txtObservacoes.setPrefRowCount(4);

        if (feedbackSelecionado != null) {
            txtProfissional.setText(feedbackSelecionado.getNomeProfissional());
            dpData.setValue(feedbackSelecionado.getDataAvaliacao());
            txtObservacoes.setText(feedbackSelecionado.getObservacoes());
        }

        GridPane grade = new GridPane();
        grade.setVgap(10); grade.setHgap(10); grade.setAlignment(Pos.CENTER);
        grade.add(lblProfissional, 0, 0); grade.add(txtProfissional, 1, 0);
        grade.add(lblData, 0, 1); grade.add(dpData, 1, 1);
        grade.add(lblObservacoes, 0, 2); grade.add(txtObservacoes, 1, 2);

        Button btnSalvar = new Button(feedbackSelecionado == null ? "Cadastrar" : "Salvar Alterações");
        btnSalvar.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnExcluir.setVisible(feedbackSelecionado != null);

        Button btnCancelar = new Button("Cancelar e Voltar");
        Label lblResultado = new Label();

        btnSalvar.setOnAction(e -> {
            String profissional = txtProfissional.getText().trim();
            String observacoes = txtObservacoes.getText().trim();

            // 1. Validação: Campos obrigatórios e Data
            if (profissional.isEmpty() || dpData.getValue() == null || observacoes.isEmpty()) {
                lblResultado.setText("Erro: Preencha todos os campos, incluindo a data e observações!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            // 2. Validação: O texto de observação não pode ser muito curto
            // Exige pelo menos 10 caracteres para garantir que não enviem algo vazio
            if (observacoes.length() < 10) {
                lblResultado.setText("Erro: As observações devem ter pelo menos 10 caracteres.");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            if (feedbackSelecionado == null) {
                ArrayList<Feedback> todos = ArquivoFeedback.lerLista();
                int novoId = Feedback.descobrirIdLivre(todos);

                Feedback novo = new Feedback(novoId, txtProfissional.getText(), txtObservacoes.getText(), dpData.getValue());
                ArquivoFeedback.adicionarFeedback(novo);
            } else {
                Feedback atualizado = new Feedback(feedbackSelecionado.getId(), txtProfissional.getText(), txtObservacoes.getText(), dpData.getValue());
                ArquivoFeedback.atualizarFeedback(atualizado);
            }

            FeedbackListaController telaLista = new FeedbackListaController(stage);
            telaLista.mostrar();
        });

        btnExcluir.setOnAction(e -> {
            ArquivoFeedback.excluirFeedback(feedbackSelecionado.getId());
            FeedbackListaController telaLista = new FeedbackListaController(stage);
            telaLista.mostrar();
        });

        btnCancelar.setOnAction(e -> {
            FeedbackListaController telaLista = new FeedbackListaController(stage);
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