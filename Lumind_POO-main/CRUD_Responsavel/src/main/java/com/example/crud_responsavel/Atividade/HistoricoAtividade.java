package com.example.crud_responsavel.Atividade;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Padrão MEMENTO - o "Caretaker" (zelador).
 * Guarda as "fotos" (mementos) das atividades antes de serem alteradas ou
 * excluídas, e sabe desfazer a última operação.
 *
 * Também é um Singleton: as telas são recriadas a cada navegação, então o
 * histórico precisa viver fora delas, numa única instância.
 */
public final class HistoricoAtividade {

    private static final HistoricoAtividade INSTANCIA = new HistoricoAtividade();

    // Uma entrada do histórico: a foto + o tipo de operação que foi feita
    private record Registro(Atividade.Memento memento, boolean eraExclusao) {
    }

    private final Deque<Registro> pilha = new ArrayDeque<>();

    private HistoricoAtividade() {
    }

    public static HistoricoAtividade getInstancia() {
        return INSTANCIA;
    }

    // Chamar ANTES de alterar uma atividade
    public void guardarAntesDeAlterar(Atividade atividade) {
        pilha.push(new Registro(atividade.salvarEstado(), false));
    }

    // Chamar ANTES de excluir uma atividade
    public void guardarAntesDeExcluir(Atividade atividade) {
        pilha.push(new Registro(atividade.salvarEstado(), true));
    }

    public boolean podeDesfazer() {
        return !pilha.isEmpty();
    }

    // Desfaz a última alteração ou exclusão. Devolve false se não há nada a desfazer.
    public boolean desfazer() {
        if (pilha.isEmpty()) {
            return false;
        }

        Registro registro = pilha.pop();
        Atividade restaurada = Atividade.restaurarDe(registro.memento());

        if (registro.eraExclusao()) {
            // Foi excluída: precisa ser recriada.
            // O ID pode ter sido reaproveitado por outra atividade nova, então confere.
            boolean idOcupado = ArquivoAtividade.lerLista().stream()
                    .anyMatch(a -> a.getId() == restaurada.getId());
            if (idOcupado) {
                restaurada.setId(Atividade.descobrirIdLivre(ArquivoAtividade.lerLista()));
            }
            ArquivoAtividade.adicionarAtividade(restaurada);
        } else {
            // Foi alterada: volta para os dados antigos
            ArquivoAtividade.atualizarAtividade(restaurada);
        }
        return true;
    }
}
