import { createSignalStore, PatchFunction } from "@ngrx/signals";
import { Tarefa } from "../tarefa.model";

interface TarefaState {
    tarefas: Tarefa[];
}

const initialState: TarefaState = {
    tarefas: [
        { id: '1', descricao: 'Estudar Angular na residencia TIC18 do CEPEDI' },
        { id: '2', descricao: 'Estudar NgRx na residencia TIC18 do CEPEDI' },
        { id: '3', descricao: 'Estudar Redux na residencia TIC18 do CEPEDI' },
    ]
};

const tarefaStore = createSignalStore(initialState, (patch: PatchFunction<TarefaState>) => ({
    adicionarTarefa(tarefa: Tarefa) {
        patch((estado) => ({
            ...estado,
            tarefas: [...estado.tarefas, tarefa]
        }));
    },
    removerTarefa(id: string) {
        patch((estado) => ({
            ...estado,
            tarefas: estado.tarefas.filter(tarefa => tarefa.id !== id)
        }));
    }
}));

export default tarefaStore;
