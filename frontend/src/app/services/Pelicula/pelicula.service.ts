import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { MovieResponse } from '../../models/movie.model';

@Injectable({
  providedIn: 'root'
})
export class PeliculaService {
  private apiUrl:string = environment.backendUrl+'/content/movies';
  
  constructor(private http: HttpClient) {}

  //peticion para busqueda de peliculas con nombre
  searchMovies(query: string): Observable<MovieResponse[]>{
    return this.http.get<MovieResponse[]>(`${this.apiUrl}/search`, {params: {query}});
  }

  //peticion para obtener peliculas actualmente en emision
  getNowPlaying(): Observable<MovieResponse[]>{
    return this.http.get<MovieResponse[]>(`${this.apiUrl}/recent`);
  }

  //peticion para obtener detalles de peliculas
  getMovieDetails(movieId: number): Observable<MovieResponse>{
    return this.http.get<MovieResponse>(`${this.apiUrl}/details/${movieId}`);
  }

}