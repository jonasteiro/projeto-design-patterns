package com.example.crud_responsavel;

/**
 * Padrão SINGLETON.
 * Guarda o usuário que está logado no sistema. Existe uma única instância
 * em todo o programa, então qualquer tela pode consultar quem está logado
 * sem precisar receber o Usuario pelo construtor.
 */
public class SessaoUsuario {

    // A única instância, criada uma vez só quando a classe é carregada
    private static final SessaoUsuario INSTANCIA = new SessaoUsuario();

    private Usuario usuarioLogado;

    // Construtor privado: ninguém de fora consegue fazer "new SessaoUsuario()"
    private SessaoUsuario() {
    }

    // Ponto de acesso global à única instância
    public static SessaoUsuario getInstancia() {
        return INSTANCIA;
    }

    public void iniciar(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void encerrar() {
        this.usuarioLogado = null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
