import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { MovieResponse } from '../../models/movie.model';
import { AlbumResponse } from '../../models/music.model';
import { FavoriteAlbumsRequest, FavoriteAlbumsResponse, FavoriteMoviesRequest, FavoriteMoviesResponse } from '../../models/favorite.model';
import { AuthService } from '../Autentificador/auth.service';
import { DeleteResponse } from '../../models/delete.model';

@Injectable({
  providedIn: 'root'
})
export class PreferenciaService {
  private apiUrl = `${environment.backendUrl}/interaction/favorites`; 

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  // Obtener películas favoritas de un usuario
  getPeliculasFavoritas(token: string, userId: number): Observable<MovieResponse[]> {
    return this.http.get<MovieResponse[]>(`${this.apiUrl}/movies/user/${userId}`, { headers: this.getHeaders() });
  }

  // Obtener álbumes favoritos de un usuario
  getAlbumesFavoritos(token: string, userId: number): Observable<AlbumResponse[]> {
    return this.http.get<AlbumResponse[]>(`${this.apiUrl}/albums/user/${userId}`, { headers: this.getHeaders() });
  }

  // Agregar película a favoritos
  agregarPeliculaFavorita(token: string, userId: number, movieId: number): Observable<FavoriteMoviesResponse> {
    const request: FavoriteMoviesRequest = { userId, movieId };
    return this.http.post<FavoriteMoviesResponse>(`${this.apiUrl}/movies`, request, { headers: this.getHeaders() });
  }

  // Agregar álbum a favoritos
  agregarAlbumFavorito(token: string, userId: number, albumId: number): Observable<FavoriteAlbumsResponse> {
    const request: FavoriteAlbumsRequest = { userId, albumId };
    return this.http.post<FavoriteAlbumsResponse>(`${this.apiUrl}/albums`, request, { headers: this.getHeaders() });
  }

  // Método para eliminar una película de favoritos
  removeFavoriteMovie(userId: number, movieId: number, authToken: string): Observable<DeleteResponse> {
    const params = new HttpParams().set('userId', userId.toString());
    return this.http.delete<DeleteResponse>(`${this.apiUrl}/movies/delete/${movieId}`, { headers: this.getHeaders(), params });
  }

  // Método para eliminar un álbum de favoritos
  removeFavoriteAlbum(userId: number, albumId: number, authToken: string): Observable<DeleteResponse> {
    const params = new HttpParams().set('userId', userId.toString());
    return this.http.delete<DeleteResponse>(`${this.apiUrl}/albums/delete/${albumId}`, { headers: this.getHeaders(), params });
  }

  // Método combinado para eliminar cualquier tipo de favorito
  removeFavoriteItem(userId: number, itemId: number, itemType: 'album' | 'movie', authToken: string): Observable<DeleteResponse> {
    const params = new HttpParams().set('userId', userId.toString());
    const endpoint = itemType === 'album' ? 'albums/delete' : 'movies/delete';
    
    return this.http.delete<DeleteResponse>(`${this.apiUrl}/${endpoint}/${itemId}`, { headers: this.getHeaders(), params });
  }
}
