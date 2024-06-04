import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-user-files',
  standalone: true,
  imports: [CommonModule, ButtonModule, TableModule, ConfirmDialogModule, ToastModule],
  providers: [ConfirmationService, MessageService],
  templateUrl: './user-files.component.html',
  styleUrls: ['./user-files.component.css']
})
export class UserFilesComponent implements OnInit {
  userId!: string;
  user: any = {};
  files: any[] = [];

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.userId = this.route.snapshot.paramMap.get('id') || '';
    if (!this.userId) {
      this.router.navigate(['/admin/users']);
      return;
    }
    this.fetch();
  }

  async confirmDeleteFile(trainingId: string, filename: string, originalName: string) {
    this.confirmationService.confirm({
      message: `<p>Deseja realmente excluir o arquivo abaixo?</p> <p class="bold underlined">${originalName}</p>`,
      accept: () => {
        this.deleteFile(trainingId, filename);
      }
    });
  }

  private async deleteFile(trainingId: string, filename: string) {
    await this.userService.deleteFileObject(this.userId, filename);
    await this.userService.deleteTraining(this.userId, trainingId);
    this.fetch();
    this.messageService.add({ severity: 'success', summary: 'Arquivo excluído', detail: 'Arquivo excluído com sucesso' });
  }

  private async fetch() {
    this.user = await this.userService.getById(this.userId);
    this.files = await this.userService.getFilesById(this.userId);
  }
}
