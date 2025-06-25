import { Component, OnInit } from '@angular/core';
import { Preferencia } from '../../models/preferencia.model';
import { PreferenciaService } from '../../services/Preferencia/preferencia.service';
import { AuthService } from '../../services/Autentificador/auth.service';
import { MovieResponse } from '../../models/movie.model';
import { AlbumResponse } from '../../models/music.model';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-favorites',
  imports: [],
  templateUrl: './favorites.component.html',
  styleUrl: './favorites.component.css'
})
export class FavoritesComponent implements OnInit{
  movies: any[] = [];
  albums: any[] = [];

  constructor(private preferenciaService: PreferenciaService, private authService: AuthService) {}

  ngOnInit(): void {
    this.obtenerAlbumesFavoritos();
    this.obtenerPeliculasFavoritas();
  }

  obtenerAlbumesFavoritos(): void {
    const token = this.authService.getToken();
    
    if (!token) {
      console.error('No se encontró token de autenticación');
      return;
    }

    // Asumiendo que decodeToken() devuelve un objeto con la propiedad 'sub' que contiene el userId
    const userId = this.authService.decodeToken().sub;
    
    if (!userId) {
      console.error('No se pudo obtener el userId del token');
      return;
    }

    this.preferenciaService.getAlbumesFavoritos(token, Number(userId)).subscribe({
      next: (albumes: AlbumResponse[]) => {
        this.albums = albumes;
        console.log('Álbumes favoritos obtenidos:', albumes);
      },
      error: (error) => {
        console.error('Error al obtener álbumes favoritos:', error);
        // Opcional: mostrar mensaje al usuario
        // this.snackBar.open('Error al obtener álbumes favoritos', 'Cerrar', { duration: 3000 });
      }
    });
  }

  obtenerPeliculasFavoritas(): void {
    const token = this.authService.getToken();
    
    if (!token) {
      console.error('No se encontró token de autenticación');
      return;
    }

    const userId = this.authService.decodeToken().sub;
    
    if (!userId) {
      console.error('No se pudo obtener el userId del token');
      return;
    }

    this.preferenciaService.getPeliculasFavoritas(token, Number(userId)).subscribe({
      next: (peliculas: MovieResponse[]) => {
        this.movies = peliculas;
        console.log('Películas favoritas obtenidas:', peliculas);
      },
      error: (error) => {
        console.error('Error al obtener películas favoritas:', error);
        // Opcional: mostrar mensaje al usuario
        // this.snackBar.open('Error al obtener películas favoritas', 'Cerrar', { duration: 3000 });
      }
    });
  }

  RemoveMovie(movieId: number): void {
    const token = this.authService.getToken();

    if (!token) {
      console.error('No se encontró token de autenticación');
      return;
    }

    const userId = this.authService.decodeToken().sub;
    
    if (!userId) {
      console.error('No se pudo obtener el userId del token');
      return;
    }

    this.preferenciaService.removeFavoriteMovie(userId, movieId, token).subscribe({
      next: (response) => {
        if (response.success) {
          alert(`Película ${movieId} eliminada de favoritos`);
          this.movies = this.movies.filter(m => m.id !== movieId);
        } else {
          alert(`Error: ${response.message}`);
        }
      },
      error: (err) => {
        console.error('Error en la solicitud:', err);
        alert('Error al comunicarse con el servidor');
      }
    });
  }

  removeAlbum(albumId: number) {
    const token = this.authService.getToken();

    if (!token) {
      console.error('No se encontró token de autenticación');
      return;
    }

    const userId = this.authService.decodeToken().sub;
    
    if (!userId) {
      console.error('No se pudo obtener el userId del token');
      return;
    }

    this.preferenciaService.removeFavoriteItem(userId, albumId, 'album', token).subscribe({
      next: (response) => {
        if (response.success) {
          alert(`Álbum ${albumId} eliminado de favoritos`);
          this.albums = this.albums.filter(a => a.id !== albumId);
        } else {
          alert(`Error: ${response.message}`);
        }
      },
      error: (err) => {
        console.error('Error en la solicitud:', err);
        alert('Error al comunicarse con el servidor');
      }
    });
  }
  
}
