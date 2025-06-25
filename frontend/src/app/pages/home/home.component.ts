
import { Component, OnInit } from '@angular/core';
import { PeliculaService } from '../../services/Pelicula/pelicula.service';
import { Router } from '@angular/router';
import { MusicService } from '../../services/Musica/music.service';
import { PreferenciaService } from '../../services/Preferencia/preferencia.service';
import { AuthService } from '../../services/Autentificador/auth.service';
import { RecomendacionComponent } from '../../components/recomendacion/recomendacion.component';
import { FavoriteAlbumsResponse, FavoriteMoviesResponse } from '../../models/favorite.model';
import { ReleaseDatePipe } from '../../pipes/releaseDate.pipe';

@Component({
  selector: 'app-home',
  imports: [RecomendacionComponent, ReleaseDatePipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  movies: any[] = [];
  albums: any[] = [];
  isLoggedIn = false;

  constructor(private peliculaService: PeliculaService, private spotifyService: MusicService,
    private preferenciaService:PreferenciaService, private authService: AuthService, private router:Router) {}


  ngOnInit(): void {
    this.loadRecentMovies();
    this.loadRecentAlbums();
    this.isLoggedIn = this.authService.isLoggedIn();
  }

  loadRecentAlbums(): void{
    this.spotifyService.getNewReleases().subscribe({
      next: (response) => {
        this.albums=response; //spotify
      },
      error:(err) =>{
        console.error('Error al cargar los temas:', err)
      }
    })
  }

  loadRecentMovies(): void {
    this.peliculaService.getNowPlaying().subscribe({
      next: (response) => {
        this.movies = response;
      },
      error: (err) => {
        console.error('Error al cargar las películas:', err);
      },
    });
  }

  goToMovieDetails(movieId: number): void {
    this.router.navigate(['/movie', movieId]); // Navega a la ruta con el ID de la película
  }

  goToAlbumDetails(albumId: number): void{
    this.router.navigate(['/album', albumId]);
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
