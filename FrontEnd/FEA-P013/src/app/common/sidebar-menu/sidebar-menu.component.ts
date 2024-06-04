import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AuthService } from '../../services/auth/auth.service';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-sidebar-menu',
  templateUrl: './sidebar-menu.component.html',
  styleUrls: ['./sidebar-menu.component.css']
})
export class SidebarMenuComponent implements OnInit {
  user: any;
  menuGroups: any[];

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    if (!this.user) {
      this.router.navigate(['/auth/login']);
      return;
    }

    this.userService.getByLoginId(this.user.uid).then((user: any) => {
      this.user.admin = user.admin || false;
      this.initializeMenu();
    });
  }

  initializeMenu(): void {
    this.menuGroups = [
      {
        name: 'Administrador',
        admin: true,
        items: [
          { label: 'Usuários', routerLink: '/admin/users' },
        ] as MenuItem[],
      },
      {
        name: 'Usuário',
        admin: false,
        items: [
          { label: 'Perfil', routerLink: '/me/profile' },
          { label: 'Arquivos', routerLink: '/me/files' },
        ] as MenuItem[],
      },
      {
        name: 'Outros',
        admin: false,
        items: [
          { label: 'Guia', routerLink: '/help' },
        ] as MenuItem[],
      }
    ];
  }

  navigate(routerLink: string): void {
    this.router.navigate([routerLink]);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
