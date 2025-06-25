import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { UserRequest } from '../../models/user.model';
import { AuthRequest, AuthResponse } from '../../models/auth.model';
import { TokenRequest, TokenValidationResponse } from '../../models/token.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = `${environment.backendUrl}/auth`;

  constructor(private http: HttpClient, private router: Router) {}

  //registro de usuario
  register(userData: UserRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, userData).pipe(
      tap(response => {
        this.saveTokens(response.accessToken, response.refreshToken);
      })
    );
  }

  //inicio de sesión
  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        this.saveTokens(response.accessToken, response.refreshToken);
      })
    );
  }

  //guardar tokens en localStorage
  private saveTokens(accessToken: string, refreshToken: string): void {
    localStorage.setItem('access_token', accessToken);
    localStorage.setItem('refresh_token', refreshToken);
  }

  //obtener access token
  getToken(): string | null {
    return localStorage.getItem('access_token');
  }

  // Verificar si está autenticado
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Cerrar sesión
  logout(): void {
    const refreshToken = localStorage.getItem('refresh_token');
    
    if (refreshToken) {
      this.http.post(`${this.apiUrl}/logout`, { token: refreshToken } as TokenRequest).subscribe();
    }
    
    this.clearTokens();
    this.router.navigate(['/home']);
  }

  // Limpiar tokens
  private clearTokens(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
  }

  // Decodificar token
  decodeToken(): any {
    const token = this.getToken();
    if (token) {
      try {
        console.log(jwtDecode(token));
        return jwtDecode(token);
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  }

  // Validar token con el backend (usando TokenRequest)
  validateToken(): Observable<TokenValidationResponse> {
    const token = this.getToken();
    if (!token) {
      throw new Error('No token available');
    }
    return this.http.post<TokenValidationResponse>(`${this.apiUrl}/validate`, { token } as TokenRequest);
  }

  // Refrescar token (usando TokenRequest)
  refreshToken(): Observable<AuthResponse> {
    const refreshToken = localStorage.getItem('refresh_token');
    if (!refreshToken) {
      this.router.navigate(['/login']);
      throw new Error('No refresh token available');
    }

    return this.http.post<AuthResponse>(`${this.apiUrl}/refresh`, { token: refreshToken } as TokenRequest).pipe(
      tap(response => {
        this.saveTokens(response.accessToken, response.refreshToken);
      })
    );
  }

  // Obtener información del usuario desde el token
  getCurrentUser(): { username: string, id: string, role: string } | null {
    const decoded = this.decodeToken();
    if (decoded) {
      return {
        username: decoded.name, // Según tu JwtUtils, el username está en "name"
        id: decoded.sub,     // El subject es el email según tu implementación
        role: decoded.role      // El rol está en "role" según tu JwtUtils
      };
    }
    return null;
  }
  /*private apiUrl = `${environment.backendUrl}/v1/auth`;

  constructor(private http: HttpClient, private router: Router) {}

  //se registra el usuario en la base de datos
  register(userData: UserDTO): Observable<AuthDTO> {
    return this.http.post<AuthDTO>(`${this.apiUrl}/register`, userData).pipe(
      tap(response => this.saveToken(response.token))
    );
  }

  //se reliza la peticion Post de inicio de sesion recibiendo el nombre de usuario y contraseña
  login(credentials: LoginDTO): Observable<AuthDTO> {
    return this.http.post<AuthDTO>(`${this.apiUrl}/login`, credentials).pipe(
      tap(respose => this.saveToken(respose.token))
    );
  }

  //Se guarda el token generado por el JWT
  saveToken(token: string): void {
    localStorage.setItem('auth_token', token);
  }

  //se obtiene el token que se encuentra en el localStorage
  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  //verifica si hay una sesion activa verificando si hay un token en el local storage
  isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token;
  }

  //cierra la sesion eliminando el token de inicio de sesion del local storage
  logout(): void {
    localStorage.removeItem('auth_token');
    this.router.navigate(['/home']);
  }

  //Decodifica el token para obtener datos como el nombre de usuario o correo y ser mostrados en la pagina
  decodeToken(): any {
    const token = this.getToken();
    if (token) {
      try {
        console.log(jwtDecode(token));
        return jwtDecode(token);
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  }*/
}
