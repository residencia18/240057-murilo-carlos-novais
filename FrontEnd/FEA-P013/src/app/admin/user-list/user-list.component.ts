import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  userId: string = '';
  user: any = {};
  addUserDialogVisible: boolean = false;
  editUserDialogVisible: boolean = false;
  uploadFileDialogVisible: boolean = false;
  users: any[] = [];
  search: string = '';

  constructor(
    private userService: UserService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.fetch();
  }

  filterUserList(): void {
    this.users = this.users.filter(user =>
      user.name.toUpperCase().includes(this.search.toUpperCase())
    );
  }

  showEditUserDialog(user: any): void {
    this.user = user;
    this.editUserDialogVisible = true;
  }

  showUploadFileDialog(id: string): void {
    this.userId = id;
    this.uploadFileDialogVisible = true;
  }

  deleteUser(id: string): void {
    this.userService.delete(id).then(() => {
      this.fetch();
      this.messageService.add({
        severity: 'success',
        summary: 'Usuário excluído',
        detail: 'Usuário excluído com sucesso',
      });
    });
  }

  confirmDeleteUser(id: string, name: string): void {
    this.confirmationService.confirm({
      message: `<p>Deseja realmente excluir o usuário abaixo?</p><p class="bold underlined">${name}</p>`,
      accept: () => {
        this.deleteUser(id);
      },
    });
  }

  async fetch(): Promise<void> {
    const allUsers = await this.userService.getAll();
    this.users = allUsers.filter(user => !user.admin);
  }

  getLastFile(user: any): any {
    return user.trainings && user.trainings.length > 0 ? user.trainings[0] : {
      name: '',
      url: '',
      createdAt: ''
    };
  }
}
