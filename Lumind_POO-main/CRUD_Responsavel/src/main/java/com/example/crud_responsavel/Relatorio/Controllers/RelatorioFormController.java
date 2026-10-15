package com.example.crud_responsavel.Relatorio.Controllers;

import com.example.crud_responsavel.SessaoUsuario;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Relatorio.Relatorio;
import com.example.crud_responsavel.Relatorio.ArquivoRelatorio;


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

public class RelatorioFormController {
    private Stage stage;
    private Scene cena;
    private Relatorio relatorioSelecionado;

    public RelatorioFormController(Stage stage, Relatorio relatorioSelecionado) {
        this.stage = stage;
        this.relatorioSelecionado = relatorioSelecionado;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle(relatorioSelecionado == null ? "Novo Relatório" : "Gerenciar Relatório");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label(relatorioSelecionado == null ? "Cadastrar Relatório" : "Alterar / Excluir Relatório");
        lblTitulo.setFont(new Font("Arial", 20));

        Label lblIdPessoaTea = new Label("ID do Paciente TEA:");
        TextField txtIdPessoaTea = new TextField();

        Label lblData = new Label("Data do Relatório:");
        DatePicker dpData = new DatePicker();

        Label lblDescricao = new Label("Descrição:");
        TextArea txtDescricao = new TextArea();
        txtDescricao.setPrefRowCount(4);

        if (relatorioSelecionado != null) {
            txtIdPessoaTea.setText(String.valueOf(relatorioSelecionado.getIdPessoaTea()));
            dpData.setValue(relatorioSelecionado.getData());
            txtDescricao.setText(relatorioSelecionado.getDescricao());
        }

        GridPane grade = new GridPane();
        grade.setVgap(10); grade.setHgap(10); grade.setAlignment(Pos.CENTER);
        grade.add(lblIdPessoaTea, 0, 0); grade.add(txtIdPessoaTea, 1, 0);
        grade.add(lblData, 0, 1); grade.add(dpData, 1, 1);
        grade.add(lblDescricao, 0, 2); grade.add(txtDescricao, 1, 2);

        Button btnSalvar = new Button(relatorioSelecionado == null ? "Cadastrar" : "Salvar Alterações");
        btnSalvar.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnExcluir.setVisible(relatorioSelecionado != null);

        Button btnCancelar = new Button("Cancelar e Voltar");
        Label lblResultado = new Label();

        btnSalvar.setOnAction(e -> {
            if (txtIdPessoaTea.getText().isEmpty() || dpData.getValue() == null || txtDescricao.getText().isEmpty()) {
                lblResultado.setText("Erro: Preencha todos os campos!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            int idPessoa = 0;
            try {
                idPessoa = Integer.parseInt(txtIdPessoaTea.getText());
            } catch (NumberFormatException ex) {
                lblResultado.setText("Erro: O ID do Paciente deve ser um número!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            if (relatorioSelecionado == null) {
                ArrayList<Relatorio> todos = ArquivoRelatorio.lerLista();
                int novoId = Relatorio.descobrirIdLivre(todos);

                // Usa o ID do usuarioLogado como ID do Responsavel
                Relatorio novo = new Relatorio(novoId, idPessoa, SessaoUsuario.getInstancia().getUsuarioLogado().getId(), txtDescricao.getText(), dpData.getValue());
                ArquivoRelatorio.adicionarRelatorio(novo);
            } else {
                Relatorio atualizado = new Relatorio(relatorioSelecionado.getId(), idPessoa, SessaoUsuario.getInstancia().getUsuarioLogado().getId(), txtDescricao.getText(), dpData.getValue());
                ArquivoRelatorio.atualizarRelatorio(atualizado);
            }

            RelatorioListaController telaLista = new RelatorioListaController(stage);
            telaLista.mostrar();
        });

        btnExcluir.setOnAction(e -> {
            ArquivoRelatorio.excluirRelatorio(relatorioSelecionado.getId());
            RelatorioListaController telaLista = new RelatorioListaController(stage);
            telaLista.mostrar();
        });

        btnCancelar.setOnAction(e -> {
            RelatorioListaController telaLista = new RelatorioListaController(stage);
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