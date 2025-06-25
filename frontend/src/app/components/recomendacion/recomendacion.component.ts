import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/Autentificador/auth.service';
import { RecomendacionService } from '../../services/Recomendaciones/recomendacion.service';
import { Preferencia } from '../../models/preferencia.model';
import { Router } from '@angular/router';
import { PreferenciaService } from '../../services/Preferencia/preferencia.service';
import { FavoriteAlbumsResponse, FavoriteMoviesResponse } from '../../models/favorite.model';
import { MovieResponse } from '../../models/movie.model';
import { AlbumResponse } from '../../models/music.model';
import { ReleaseDatePipe } from "../../pipes/releaseDate.pipe";

@Component({
  selector: 'app-recomendacion',
  imports: [ReleaseDatePipe],
  templateUrl: './recomendacion.component.html',
  styleUrl: './recomendacion.component.css'
})
export class RecomendacionComponent implements OnInit {
  movieRecommendations: any[] = [];
  albumRecommendations: AlbumResponse[] = [];
  loading = false;
  error: string | null = null;

  constructor(private recomendacionService: RecomendacionService, private authService: AuthService, private preferenciaService:PreferenciaService, private router:Router) {}

  ngOnInit(): void {
    //this.obtenerRecomendacionesPeliculas();
    this.refreshRecommendations();
    this.loadRecommendations();
  }

  loadRecommendations(): void {
    const userId = this.authService.decodeToken().sub;
    if (!userId) {
      this.error = 'Usuario no autenticado';
      return;
    }

    this.loading = true;
    this.error = null;

    this.recomendacionService.getAllRecommendations(userId).subscribe({
      next: ({movies, albums}) => {
        this.movieRecommendations = movies;
        this.albumRecommendations = albums;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar recomendaciones';
        this.loading = false;
        console.error(err);
      }
    });
  }

  refreshRecommendations(): void {
    const userId = this.authService.decodeToken().sub;
    if (!userId) {
      this.error = 'Usuario no autenticado';
      return;
    }

    this.loading = true;
    this.recomendacionService.generateRecommendations(userId).subscribe({
      next: () => {
        this.loadRecommendations();
      },
      error: (err) => {
        this.error = 'Error al generar nuevas recomendaciones';
        this.loading = false;
        console.error(err);
      }
    });
  }


  //obtiene recomendaciones de peliculas dependiendo del usuario y sus preferencias agregadas
  obtenerRecomendacionesPeliculas(): void {
    this.loading = true;
    const token = this.authService.getToken();
  
    if (token) {
      this.recomendacionService.getRecomendacionesPeliculas(token).subscribe({
        next: (response) => {
          this.movieRecommendations = response;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al cargar las recomendaciones:', err);
          this.error = 'No se pudieron cargar las recomendaciones.';
          this.loading = false;
        },
      });
    } else {
      this.error = 'No se encontró el token de autenticación.';
    }
  }
  
  //Navega a la ruta con el ID de la pelicula
  goToMovieDetails(movieId: number): void {
      this.router.navigate(['/movie', movieId]); 
  }

  goToAlbumDetails(album: number): void {
      this.router.navigate(['/album', album]); 
  }


  addToFavorites(tipo: 'pelicula' | 'album', referenciaId: number): void {
    const token: string | null = this.authService.getToken();
    const userId = this.authService.decodeToken().sub
    if (!token) {
      alert('Error, no se ha iniciado sesión');
      return;
    }

    if (tipo === 'pelicula') {
      this.preferenciaService.agregarPeliculaFavorita(token, userId, referenciaId).subscribe({
        next: (response: FavoriteMoviesResponse) => {
          console.log('Película agregada a favoritos:', response);
          alert('¡Película agregada a favoritos con éxito!');
        },
        error: (err) => {
          console.error('Error al agregar película a favoritos:', err);
          alert('Error al agregar película a favoritos.');
        }
      });
    } else if (tipo === 'album') {
      this.preferenciaService.agregarAlbumFavorito(token, userId, referenciaId).subscribe({
        next: (response: FavoriteAlbumsResponse) => {
          console.log('Álbum agregado a favoritos:', response);
          alert('¡Álbum agregado a favoritos con éxito!');
        },
        error: (err) => {
          console.error('Error al agregar álbum a favoritos:', err);
          alert('Error al agregar álbum a favoritos.');
        }
      });
    }
  }

}