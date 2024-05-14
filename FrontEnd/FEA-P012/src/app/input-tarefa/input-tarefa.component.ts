import { Component } from '@angular/core';
import { Tarefa } from '../tarefa.model';
import { TarefaStoreService } from '../store/tarefas.store'; // Importe o serviço correto
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Importe FormBuilder e FormGroup caso necessário

@Component({
  selector: 'app-input-tarefa',
  templateUrl: './input-tarefa.component.html',
  styleUrls: ['./input-tarefa.component.css']
})
export class InputTarefaComponent {
  newTask = '';

  constructor(private tarefaStore: TarefaStoreService) { } // Injeção de dependência do serviço

  addTask() {
    if (this.newTask.trim() === '') {
      return; // Não adiciona tarefa vazia
    }

    const newTarefa: Tarefa = {
      id: this.generateId(),
      descricao: this.newTask,
    };

    this.tarefaStore.adicionarTarefa(newTarefa);

    this.newTask = ''; // Limpa o campo após adicionar a tarefa
  }

  generateId(): string {
    return Date.now().toString(36) + Math.random().toString(36).substring(2);
  }
}
