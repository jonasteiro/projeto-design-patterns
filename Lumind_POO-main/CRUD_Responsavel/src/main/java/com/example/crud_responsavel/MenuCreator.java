package com.example.crud_responsavel;

import com.example.crud_responsavel.Administrador.Controllers.MenuAdministradorController;
import com.example.crud_responsavel.PessoaTea.Controllers.MenuPessoaTeaController;
import com.example.crud_responsavel.Profissional.Controllers.MenuProfissionalController;
import com.example.crud_responsavel.Responsavel.Controllers.MenuResponsavelController;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;

/**
 * Padrão FACTORY METHOD - classe "criadora".
 * Cada subclasse decide qual menu criar (criarMenu). Quem usa só chama
 * abrirMenu() e não precisa saber qual menu concreto será aberto.
 */
public abstract class MenuCreator {

    // FACTORY METHOD: cada subclasse implementa e devolve o seu menu
    protected abstract MenuUsuario criarMenu(Stage stage);

    // Código comum: cria o menu (sem saber qual é) e mostra na tela
    public void abrirMenu(Stage stage) {
        MenuUsuario menu = criarMenu(stage);
        menu.mostrar();
    }

    // Relaciona cada Tipo de usuário com o seu criador de menu
    private static final Map<Tipo, MenuCreator> CRIADORES = new EnumMap<>(Tipo.class);

    static {
        CRIADORES.put(Tipo.ADMINISTRADOR, new MenuAdministradorCreator());
        CRIADORES.put(Tipo.PROFISSIONAL_SAUDE, new MenuProfissionalCreator());
        CRIADORES.put(Tipo.RESPONSAVEL, new MenuResponsavelCreator());
        CRIADORES.put(Tipo.PESSOA_TEA, new MenuPessoaTeaCreator());
    }

    // Devolve o criador correto para o tipo de usuário informado
    public static MenuCreator para(Tipo tipo) {
        return CRIADORES.get(tipo);
    }

    // ----- Criadores concretos -----

    static class MenuAdministradorCreator extends MenuCreator {
        @Override
        protected MenuUsuario criarMenu(Stage stage) {
            return new MenuAdministradorController(stage);
        }
    }

    static class MenuProfissionalCreator extends MenuCreator {
        @Override
        protected MenuUsuario criarMenu(Stage stage) {
            return new MenuProfissionalController(stage);
        }
    }

    static class MenuResponsavelCreator extends MenuCreator {
        @Override
        protected MenuUsuario criarMenu(Stage stage) {
            return new MenuResponsavelController(stage);
        }
    }

    static class MenuPessoaTeaCreator extends MenuCreator {
        @Override
        protected MenuUsuario criarMenu(Stage stage) {
            return new MenuPessoaTeaController(stage);
        }
    }
}
