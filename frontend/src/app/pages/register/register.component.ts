
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserDTO, UserRequest } from '../../models/user.model';
import { AuthService } from '../../services/Autentificador/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent{
  registerForm: FormGroup;
  mensajeError: string | null = null;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      birthDay: ['', Validators.required]
    });
  }

  // Registra el usuario usando usuarioService para la peticion
  onSubmit(): void {
    if (this.registerForm.valid) {
      const usuario: UserRequest = this.registerForm.value;
      this.authService.register(usuario).subscribe({
        next: () => {
          alert('Registro exitoso');
          this.registerForm.reset();
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.error(err);
          this.mensajeError = err.error.message || 'Ocurrió un error en el registro';
        }
      });
    } else {
      this.mensajeError = 'Por favor, complete correctamente todos los campos.';
    }
  }
  
}