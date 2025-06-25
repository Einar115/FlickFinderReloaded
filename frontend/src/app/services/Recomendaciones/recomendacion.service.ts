import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, forkJoin, map, Observable, of } from 'rxjs';
import { AuthService } from '../Autentificador/auth.service';
import { MovieResponse } from '../../models/movie.model';
import { AlbumResponse } from '../../models/music.model';

@Injectable({
  providedIn: 'root'
})
export class RecomendacionService {
  private apiUrl = `${environment.backendUrl}/interaction/recommendation`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  generateRecommendations(userId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/generate/${userId}`, {}, { headers: this.getHeaders() });
  }

  getMovieRecommendations(userId: number): Observable<MovieResponse[]> {
    return this.http.get<MovieResponse[]>(`${this.apiUrl}/movies/${userId}`, { headers: this.getHeaders() });
  }

  getAlbumRecommendations(userId: number): Observable<AlbumResponse[]> {
    return this.http.get<AlbumResponse[]>(`${this.apiUrl}/albums/${userId}`, { headers: this.getHeaders() });
  }

  // Método combinado para obtener ambas recomendaciones
  getAllRecommendations(userId: number): Observable<{movies: MovieResponse[], albums: AlbumResponse[]}> {
    return new Observable(observer => {
      forkJoin([
        this.getMovieRecommendations(userId),
        this.getAlbumRecommendations(userId)
      ]).subscribe({
        next: ([movies, albums]) => {
          observer.next({ movies, albums });
          observer.complete();
        },
        error: err => observer.error(err)
      });
    });
  }




  //Obtener recomendaciones de peliculas
  getRecomendacionesPeliculas(token: string): Observable<any[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.http.get<string[]>(`${this.apiUrl}/movies`, { headers }).pipe(
      map((response: string[]) => {
        const parsedResults = response.flatMap((jsonString) => {
          const parsedObject = JSON.parse(jsonString);
          return parsedObject.results || []; // Extrae el campo "results" de cada string JSON
        });
        return parsedResults;
      }),
      catchError((error) => {
        console.error('Error al procesar las recomendaciones:', error);
        return of([]);
      })
    );
  }

}