import { Component } from '@angular/core';
import { RouterLink,Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  name = '';
  email = '';
  password = '';

  constructor(private auth: AuthService, private router: Router) {}

  register() {
    this.auth.register({
      email: this.email,
      password: this.password
    }).subscribe({
      next: (res: any) => {

        this.auth.saveToken(res.token);

        this.router.navigate(['/profile']);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}
