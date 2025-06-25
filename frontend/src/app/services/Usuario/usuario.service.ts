import { Injectable } from '@angular/core';
import { UserDTO } from '../../models/user.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl:string = environment.backendUrl+'/usuarios';
  private usuarios: UserDTO[] = [];

  constructor(private http: HttpClient) {}

  //añade un usuario al arreglo de usuarios
  addUser(usuarios: UserDTO) {
    this.usuarios.push(usuarios);
  }

  //obtiene los usuarios
  getUsers(): UserDTO[] {
    return this.usuarios;
  }
  
  //peticion post para registrar usuario 
  register(usuario: UserDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, usuario);
  }

  //peticion get para obtener todos los usuarios
  getUsuarios(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.apiUrl}/all`);
  }
}
