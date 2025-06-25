import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/Autentificador/auth.service';
import { Router } from '@angular/router';
import { LoginDTO } from '../../models/login.model';
import { AuthRequest } from '../../models/auth.model';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{
  credentials: AuthRequest = {
    email: '',
    password: ''
  };
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.errorMessage = ''; // Resetea el mensaje de error

    this.authService.login(this.credentials).subscribe({
      next: () => {
        const user = this.authService.getCurrentUser();
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Error en login:', err);
        this.errorMessage = 'Credenciales inválidas o error del servidor';
      }
    });
  }
}

 