package com.example.crud_responsavel.Evento.Controllers;

import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Evento.Evento;
import com.example.crud_responsavel.Evento.ArquivoEvento;

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

public class EventoFormController {
    private Stage stage;
    private Scene cena;
    private Evento eventoSelecionado;

    public EventoFormController(Stage stage, Evento eventoSelecionado) {
        this.stage = stage;
        this.eventoSelecionado = eventoSelecionado;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle(eventoSelecionado == null ? "Novo Evento" : "Gerenciar Evento");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        Label lblTituloForm = new Label(eventoSelecionado == null ? "Cadastrar Evento" : "Alterar / Excluir Evento");
        lblTituloForm.setFont(new Font("Arial", 20));

        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField();

        Label lblLocal = new Label("Local:");
        TextField txtLocal = new TextField();

        Label lblData = new Label("Data:");
        DatePicker dpData = new DatePicker();

        Label lblDescricao = new Label("Descrição:");
        TextArea txtDescricao = new TextArea();
        txtDescricao.setPrefRowCount(3);

        if (eventoSelecionado != null) {
            txtTitulo.setText(eventoSelecionado.getTitulo());
            txtLocal.setText(eventoSelecionado.getLocal());
            dpData.setValue(eventoSelecionado.getData());
            txtDescricao.setText(eventoSelecionado.getDescricao());
        }

        GridPane grade = new GridPane();
        grade.setVgap(10); grade.setHgap(10); grade.setAlignment(Pos.CENTER);
        grade.add(lblTitulo, 0, 0); grade.add(txtTitulo, 1, 0);
        grade.add(lblLocal, 0, 1); grade.add(txtLocal, 1, 1);
        grade.add(lblData, 0, 2); grade.add(dpData, 1, 2);
        grade.add(lblDescricao, 0, 3); grade.add(txtDescricao, 1, 3);

        Button btnSalvar = new Button(eventoSelecionado == null ? "Cadastrar" : "Salvar Alterações");
        btnSalvar.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnExcluir.setVisible(eventoSelecionado != null);

        Button btnCancelar = new Button("Cancelar e Voltar");
        Label lblResultado = new Label();

        btnSalvar.setOnAction(e -> {
            String titulo = txtTitulo.getText().trim();
            String local = txtLocal.getText().trim();
            String descricao = txtDescricao.getText().trim();

            // 1. Validação: Título, Data e Local são obrigatórios
            if (titulo.isEmpty() || dpData.getValue() == null || local.isEmpty()) {
                lblResultado.setText("Erro: Título, Data e Local são obrigatórios!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            // 2. Validação: A descrição deve ter um tamanho mínimo
            if (descricao.length() < 5) {
                lblResultado.setText("Erro: A descrição deve ter pelo menos 5 caracteres.");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            if (eventoSelecionado == null) {
                ArrayList<Evento> todos = ArquivoEvento.lerLista();
                int novoId = Evento.descobrirIdLivre(todos);

                Evento novo = new Evento(novoId, txtTitulo.getText(), txtDescricao.getText(), dpData.getValue(), txtLocal.getText());
                ArquivoEvento.adicionarEvento(novo);
            } else {
                Evento atualizado = new Evento(eventoSelecionado.getId(), txtTitulo.getText(), txtDescricao.getText(), dpData.getValue(), txtLocal.getText());
                ArquivoEvento.atualizarEvento(atualizado);
            }

            EventoListaController telaLista = new EventoListaController(stage);
            telaLista.mostrar();
        });

        btnExcluir.setOnAction(e -> {
            ArquivoEvento.excluirEvento(eventoSelecionado.getId());
            EventoListaController telaLista = new EventoListaController(stage);
            telaLista.mostrar();
        });

        btnCancelar.setOnAction(e -> {
            EventoListaController telaLista = new EventoListaController(stage);
            telaLista.mostrar();
        });

        HBox botoes = new HBox(15, btnSalvar, btnExcluir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, lblTituloForm, grade, botoes, lblResultado);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.cena = new Scene(layout, 500, 450);
    }
}