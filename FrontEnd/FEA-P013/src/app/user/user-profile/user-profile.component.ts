import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { InitialsPipe } from '../../pipes/initials.pipe';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, InitialsPipe],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']  // Corrigido 'styleUrl' para 'styleUrls'
})
export class UserProfileComponent implements OnInit {
  public user: any = {};

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) {}

  async ngOnInit() {
    try {
      const loggedUser = this.authService.getUser();

      if (!loggedUser) {
        this.router.navigate(['/auth/login']);
        return;
      }

      this.user = await this.userService.getByLoginId(loggedUser.uid);
      this.user.mail = loggedUser.email;
    } catch (error) {
      console.error('Error fetching user data:', error);
      // You could also redirect or show an error message to the user
    }
  }
}
