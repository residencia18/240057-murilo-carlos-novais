import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user/user.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  @Input() visible = false;
  @Input() user: any = {};
  @Output() visibleChange = new EventEmitter<void>();

  editUserForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private messageService: MessageService) { }

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    this.editUserForm = this.formBuilder.group({
      'name': ["", [Validators.required]],
      'birthDate': ["", [Validators.required]],
      'height': ["", [Validators.required, Validators.pattern(/^\d*\.?\d*$/), Validators.min(0), Validators.max(3)]],
    });

    this.patchValue();
  }

  patchValue() {
    this.editUserForm.patchValue(this.user);
  }

  resetForm() {
    this.editUserForm.reset();
  }

  updateUser() {
    if (this.editUserForm.valid && this.editUserForm.dirty) {
      this.userService.update(this.user.id, this.editUserForm.value).then(() => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Usuário atualizado com sucesso' });
        this.closeDialog();
      });
    } else {
      this.showErrors();
    }
  }

  showErrors() {
    const controls = this.editUserForm.controls;

    Object.keys(controls).forEach(controlName => {
      const control = controls[controlName];
      if (control.invalid) {
        const messages = control.errors;
        Object.keys(messages).forEach(keyError => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: this.getErrorMsg(controlName, keyError) });
        });
      }
    });
  }

  getErrorMsg(controlName: string, keyError: string) {
    const errorMessages = {
      'name': {
        'required': 'Nome obrigatório'
      },
      'birthDate': {
        'required': 'Data de nascimento obrigatória'
      },
      'height': {
        'required': 'Altura obrigatória',
        'pattern': 'Altura inválida',
        'min': 'Altura deve ser maior ou igual a 0',
        'max': 'Altura deve ser menor ou igual a 3'
      }
    };
    return errorMessages[controlName][keyError];
  }

  closeDialog() {
    this.resetForm();
    this.visible = false;
    this.visibleChange.emit();
  }
}
