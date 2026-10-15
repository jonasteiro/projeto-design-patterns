package com.example.crud_responsavel;

import com.example.crud_responsavel.Administrador.Administrador;
import com.example.crud_responsavel.Profissional.Profissional;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. LÓGICA DE INICIALIZAÇÃO: Verifica se o sistema está vazio
        ArrayList<Usuario> lista = ArquivoUsuario.lerLista();

        if (lista.isEmpty()) {
            Administrador adminPadrao = new Administrador(
                    1, "Admin Mestre", "admin@admin.com", "admin123",
                    "000.000.000-00", LocalDate.of(2000, 1, 1),
                    "00000000000", Tipo.ADMINISTRADOR
            );
            ArquivoUsuario.adicionarUsuario(adminPadrao);

            Profissional profPadrao = new Profissional(
                    2, "Profissional Mestre", "prof@prof.com", "prof123",
                    "111.111.111-11", LocalDate.of(2000, 1, 2),
                    "11111111111", Tipo.PROFISSIONAL_SAUDE,
                    "CRM-12345",     // registroProfissional
                    "Psicologia",                    // especialidade
                    "1234567"                        // numeroIdentidade
            );
            ArquivoUsuario.adicionarUsuario(profPadrao);
        }

        // 2. REDIRECIONAMENTO: Inicia pelo Login, não mais pelo MainController
        LoginController telaLogin = new LoginController(primaryStage);
        telaLogin.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}