import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
})
export class AddUserComponent {
  @Input() visible = false;
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  registerUserForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private messageService: MessageService) {
    this.registerUserForm = this.formBuilder.group({
      'name': ["", [Validators.required]],
      'mail': ["", [Validators.required, Validators.email]],
      'birthDate': ["", [Validators.required]],
      'height': ["", [Validators.required, this.heightValidator.bind(this)]],
    });
  }

  public addUser() {
    this.registerUserForm.get('height')?.setValue(this.registerUserForm.get('height')?.value.replace(',', '.'));

    if (this.registerUserForm.valid && this.registerUserForm.dirty) {
      this.userService.create(this.registerUserForm.value).then((res: any) => {
        if (res.success == false) {
          switch (res.error.code) {
            case 'auth/email-already-in-use':
              this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Este e-mail já está cadastrado' });
              break;
            case 'auth/operation-not-allowed':
              this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'O login por senha está desabilitado' });
              break;
            case 'auth/too-many-requests':
              this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Muitas tentativas, tente mais tarde' });
              break;
          }
          return;
        }
        this.visible = false;
        this.visibleChange.emit(this.visible);
      });
    }

    this.showErrors();
  }

  public showErrors() {
    this.showFieldErrors('name', 'Nome', 'Nome obrigatório');
    this.showFieldErrors('mail', 'Email', 'Email obrigatório', 'Email inválido');
    this.showFieldErrors('birthDate', 'Data de Nascimento', 'Data de nascimento obrigatória');
    this.showHeightErrors();
  }

  private showFieldErrors(fieldName: string, fieldLabel: string, requiredErrorMessage: string, emailErrorMessage?: string) 
