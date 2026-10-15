package com.example.crud_responsavel;

/**
 * Padrão OBSERVER - interface do "observador".
 * Quem quiser ser avisado de uma mudança nos dados implementa este método.
 */
public interface Observador {
    void atualizar();
}
