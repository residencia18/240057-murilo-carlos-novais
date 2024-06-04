import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MessageService]
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  async submit(): Promise<void> {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      const response = await this.authService.loginUser(email, password);

      if (response.success) {
        this.messageService.add({
          severity: 'success',
          summary: 'Login',
          detail: 'Login realizado com sucesso'
        });
        this.router.navigate(['/']);
      } else {
        this.handleLoginError(response.error.code);
      }
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Erro',
        detail: 'Preencha todos os campos'
      });
    }
  }

  handleLoginError(errorCode: string): void {
    switch (errorCode) {
      case 'auth/invalid-credential':
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Email e/ou senha inválidos'
        });
        break;
      case 'auth/user-not-found':
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Usuário não encontrado'
        });
        break;
      case 'auth/too-many-requests':
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Muitas tentativas, tente mais tarde'
        });
        break;
      default:
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Ocorreu um erro ao tentar fazer login'
        });
        break;
    }
  }

  forgotPassword(): void {
    console.log('Desculpe, esta funcionalidade ainda não está disponível.');
  }
}
