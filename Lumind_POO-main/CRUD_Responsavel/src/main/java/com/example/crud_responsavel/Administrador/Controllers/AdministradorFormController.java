package com.example.crud_responsavel.Administrador.Controllers;

import com.example.crud_responsavel.ArquivoUsuario;
import com.example.crud_responsavel.Tipo;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.Administrador.Administrador;

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

public class AdministradorFormController {
    private Stage stage;
    private Scene cena;
    private Administrador adminSelecionado;

    public AdministradorFormController(Stage stage, Administrador adminSelecionado) {
        this.stage = stage;
        this.adminSelecionado = adminSelecionado;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle(adminSelecionado == null ? "Novo Administrador" : "Gerenciar Administrador");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label(adminSelecionado == null ? "Cadastrar Administrador" : "Alterar / Excluir Admin");
        lblTitulo.setFont(new Font("Arial", 20));

        Label lblNome = new Label("Nome:"); TextField txtNome = new TextField();
        Label lblEmail = new Label("E-mail:"); TextField txtEmail = new TextField();
        Label lblSenha = new Label("Senha:"); PasswordField txtSenha = new PasswordField();
        Label lblCpf = new Label("CPF:"); TextField txtCpf = new TextField();
        Label lblDataNasc = new Label("Data Nasc.:"); DatePicker dpDataNasc = new DatePicker();
        Label lblTelefone = new Label("Telefone:"); TextField txtTelefone = new TextField();

        if (adminSelecionado != null) {
            txtNome.setText(adminSelecionado.getNome());
            txtEmail.setText(adminSelecionado.getEmail());
            txtSenha.setText(adminSelecionado.getSenha());
            txtCpf.setText(adminSelecionado.getCpf());
            dpDataNasc.setValue(adminSelecionado.getDataNascimento());
            txtTelefone.setText(adminSelecionado.getTelefone());
        }

        GridPane grade = new GridPane();
        grade.setVgap(10); grade.setHgap(10); grade.setAlignment(Pos.CENTER);
        grade.add(lblNome, 0, 0); grade.add(txtNome, 1, 0);
        grade.add(lblEmail, 0, 1); grade.add(txtEmail, 1, 1);
        grade.add(lblSenha, 0, 2); grade.add(txtSenha, 1, 2);
        grade.add(lblCpf, 0, 3); grade.add(txtCpf, 1, 3);
        grade.add(lblDataNasc, 0, 4); grade.add(dpDataNasc, 1, 4);
        grade.add(lblTelefone, 0, 5); grade.add(txtTelefone, 1, 5);

        Button btnSalvar = new Button(adminSelecionado == null ? "Cadastrar" : "Salvar Alterações");
        btnSalvar.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnExcluir.setVisible(adminSelecionado != null);

        Button btnCancelar = new Button("Cancelar e Voltar");
        Label lblResultado = new Label();

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();
            String cpf = txtCpf.getText().trim();
            String telefone = txtTelefone.getText().trim();

            // 1. Validação: Todos os campos devem ser preenchidos
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() || dpDataNasc.getValue() == null || telefone.isEmpty()) {
                lblResultado.setText("Erro: Todos os campos são obrigatórios!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            // 2. Validação: E-mail deve conter @
            if (!email.contains("@")) {
                lblResultado.setText("Erro: O e-mail informado é inválido!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            // 3. Validação: CPF com 11 dígitos e APENAS números
            if (!cpf.matches("\\d{11}")) {
                lblResultado.setText("Erro: O CPF deve conter exatamente 11 números!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            // 4. Validação: Telefone deve ter entre 11 e 12 números
            if (!telefone.matches("\\d{11,12}")) {
                lblResultado.setText("Erro: O telefone deve ter 11 ou 12 números (com DDD)!");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            if (adminSelecionado == null) {
                ArrayList<Usuario> todosUsuarios = ArquivoUsuario.lerLista();
                int novoId = Usuario.descobrirIdLivre(todosUsuarios);

                Administrador novo = new Administrador(novoId, txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtCpf.getText(), dpDataNasc.getValue(), txtTelefone.getText(), Tipo.ADMINISTRADOR);
                ArquivoUsuario.adicionarUsuario(novo);
            } else {
                Administrador atualizado = new Administrador(adminSelecionado.getId(), txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtCpf.getText(), dpDataNasc.getValue(), txtTelefone.getText(), Tipo.ADMINISTRADOR);
                ArquivoUsuario.atualizarUsuario(atualizado);
            }

            AdministradorListaController telaLista = new AdministradorListaController(stage);
            telaLista.mostrar();
        });

        btnExcluir.setOnAction(e -> {
            ArquivoUsuario.excluirUsuario(adminSelecionado.getId());
            AdministradorListaController telaLista = new AdministradorListaController(stage);
            telaLista.mostrar();
        });

        btnCancelar.setOnAction(e -> {
            AdministradorListaController telaLista = new AdministradorListaController(stage);
            telaLista.mostrar();
        });

        HBox botoes = new HBox(15, btnSalvar, btnExcluir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, lblTitulo, grade, botoes, lblResultado);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.cena = new Scene(layout, 550, 500);
    }
}