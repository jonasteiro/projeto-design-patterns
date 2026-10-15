package com.example.crud_responsavel.PessoaTea.Controllers;

import com.example.crud_responsavel.ArquivoUsuario;
import com.example.crud_responsavel.Tipo;
import com.example.crud_responsavel.Usuario;
import com.example.crud_responsavel.PessoaTea.PessoaTea;

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

public class PessoaTeaFormController {
    private Stage stage;
    private Scene cena;
    private PessoaTea pessoaSelecionada;

    public PessoaTeaFormController(Stage stage, PessoaTea pessoaSelecionada) {
        this.stage = stage;
        this.pessoaSelecionada = pessoaSelecionada;
        this.criarComponentes();
    }

    public void mostrar() {
        this.stage.setTitle(pessoaSelecionada == null ? "Nova Pessoa TEA" : "Gerenciar Pessoa TEA");
        this.stage.setScene(this.cena);
        this.stage.show();
    }

    private void criarComponentes() {
        Label lblTitulo = new Label(pessoaSelecionada == null ? "Cadastrar Paciente TEA" : "Alterar / Excluir Paciente");
        lblTitulo.setFont(new Font("Arial", 20));

        Label lblNome = new Label("Nome:"); TextField txtNome = new TextField();
        Label lblEmail = new Label("E-mail:"); TextField txtEmail = new TextField();
        Label lblSenha = new Label("Senha:"); PasswordField txtSenha = new PasswordField();
        Label lblCpf = new Label("CPF:"); TextField txtCpf = new TextField();
        Label lblDataNasc = new Label("Data Nasc.:"); DatePicker dpDataNasc = new DatePicker();
        Label lblTelefone = new Label("Telefone:"); TextField txtTelefone = new TextField();

        Label lblNivel = new Label("Nível TEA:");
        ChoiceBox<Integer> cbNivel = new ChoiceBox<>();
        cbNivel.getItems().addAll(1, 2, 3);
        cbNivel.setValue(1);

        Label lblObs = new Label("Observação:");
        TextField txtObs = new TextField();

        if (pessoaSelecionada != null) {
            txtNome.setText(pessoaSelecionada.getNome());
            txtEmail.setText(pessoaSelecionada.getEmail());
            txtSenha.setText(pessoaSelecionada.getSenha());
            txtCpf.setText(pessoaSelecionada.getCpf());
            dpDataNasc.setValue(pessoaSelecionada.getDataNascimento());
            txtTelefone.setText(pessoaSelecionada.getTelefone());
            cbNivel.setValue(pessoaSelecionada.getNivelTea());
            txtObs.setText(pessoaSelecionada.getObservacao());
        }

        GridPane grade = new GridPane();
        grade.setVgap(10); grade.setHgap(10); grade.setAlignment(Pos.CENTER);
        grade.add(lblNome, 0, 0); grade.add(txtNome, 1, 0);
        grade.add(lblEmail, 0, 1); grade.add(txtEmail, 1, 1);
        grade.add(lblSenha, 0, 2); grade.add(txtSenha, 1, 2);
        grade.add(lblCpf, 0, 3); grade.add(txtCpf, 1, 3);
        grade.add(lblDataNasc, 0, 4); grade.add(dpDataNasc, 1, 4);
        grade.add(lblTelefone, 0, 5); grade.add(txtTelefone, 1, 5);
        grade.add(lblNivel, 0, 6); grade.add(cbNivel, 1, 6);
        grade.add(lblObs, 0, 7); grade.add(txtObs, 1, 7);

        Button btnSalvar = new Button(pessoaSelecionada == null ? "Cadastrar" : "Salvar Alterações");
        btnSalvar.setStyle("-fx-background-color: #198754; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnExcluir.setVisible(pessoaSelecionada != null);

        Button btnCancelar = new Button("Cancelar e Voltar");
        Label lblResultado = new Label();

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();
            String cpf = txtCpf.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String obs = txtObs.getText().trim();

            // 1. Validação: Todos os campos devem ser preenchidos
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() || dpDataNasc.getValue() == null || telefone.isEmpty() || obs.isEmpty()) {
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

            if (pessoaSelecionada == null) {
                ArrayList<Usuario> todosUsuarios = ArquivoUsuario.lerLista();
                int novoId = Usuario.descobrirIdLivre(todosUsuarios);

                PessoaTea novo = new PessoaTea(novoId, txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtCpf.getText(), dpDataNasc.getValue(), txtTelefone.getText(), Tipo.PESSOA_TEA, cbNivel.getValue(), txtObs.getText());
                ArquivoUsuario.adicionarUsuario(novo);
            } else {
                PessoaTea atualizado = new PessoaTea(pessoaSelecionada.getId(), txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtCpf.getText(), dpDataNasc.getValue(), txtTelefone.getText(), Tipo.PESSOA_TEA, cbNivel.getValue(), txtObs.getText());
                ArquivoUsuario.atualizarUsuario(atualizado);
            }

            PessoaTeaListaController telaLista = new PessoaTeaListaController(stage);
            telaLista.mostrar();
        });

        btnExcluir.setOnAction(e -> {
            ArquivoUsuario.excluirUsuario(pessoaSelecionada.getId());
            PessoaTeaListaController telaLista = new PessoaTeaListaController(stage);
            telaLista.mostrar();
        });

        btnCancelar.setOnAction(e -> {
            PessoaTeaListaController telaLista = new PessoaTeaListaController(stage);
            telaLista.mostrar();
        });

        HBox botoes = new HBox(15, btnSalvar, btnExcluir, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, lblTitulo, grade, botoes, lblResultado);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.cena = new Scene(layout, 550, 600);
    }
}