import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AlbumResponse } from '../../models/music.model';

@Injectable({
  providedIn: 'root'
})
export class MusicService {
  private apiUrl=environment.backendUrl+'/content/albums';

  constructor(private http: HttpClient) {}
  
  //peticion para buscar album
  searchAlbums(query: string): Observable<AlbumResponse[]>{
      return this.http.get<AlbumResponse[]>(`${this.apiUrl}/search`, {params: {query}});
  }

  //peticion para obtener nuevos albumes
  getNewReleases(): Observable<AlbumResponse[]>{
    return this.http.get<AlbumResponse[]>(`${this.apiUrl}/recent`);
  }

  //obtener detalles de albumes
  getAlbumDetails(albumId: number): Observable<AlbumResponse>{
    return this.http.get<AlbumResponse>(`${this.apiUrl}/details/${albumId}`)
  }
  
}
