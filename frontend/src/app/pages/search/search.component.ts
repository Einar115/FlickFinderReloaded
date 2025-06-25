
import { Component, input, OnInit } from '@angular/core';
import { PeliculaService } from '../../services/Pelicula/pelicula.service';

import { FormsModule, NgForm } from '@angular/forms';
import { MusicService } from '../../services/Musica/music.service';
import { PreferenciaService } from '../../services/Preferencia/preferencia.service';
import { AuthService } from '../../services/Autentificador/auth.service';
import { Preferencia } from '../../models/preferencia.model';
import { Router } from '@angular/router';
import { FavoriteAlbumsResponse, FavoriteMoviesResponse } from '../../models/favorite.model';

@Component({
  selector: 'app-search',
  imports: [FormsModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit{
  query: string = '';
  searchType: string = 'movies'; // Valor inicial
  movies: any[] = [];
  albums: any[] = [];
  tracks: any[] = [];
  isLoggedIn = false;

  constructor(private peliculaService: PeliculaService, private spotifyService: MusicService, 
    private preferenciaService: PreferenciaService, private authService: AuthService, private router:Router) {}


  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
  }

  //comprueba la opcion de busqueda entre peliculas, albumes y pistas musicales
  search(): void {
    switch (this.searchType) {
      case 'movies':
        this.searchMovies();
        break;
      case 'albums':
        this.searchAlbums();
        break;
    }
  }

  //realiza la busqueda de peliculas
  private searchMovies(): void {
    this.peliculaService.searchMovies(this.query).subscribe({
      next: (data) => {
        this.movies = data;
      },
      error: (err) => console.error(err),
    });
  }

  //realiza la busqueda de albumes
  private searchAlbums(): void {
    this.spotifyService.searchAlbums(this.query).subscribe({
      next: (response) => {
        this.albums = response;
        console.log("albumes"+this.albums)
      },
      error: (err) => console.error(err),
    });
  }

  //se redirige a la pagina de los detalles de la pelicula
  goToMovieDetails(movieId: number): void {
      this.router.navigate(['/movie', movieId]); 
  }

  goToAlbumDetails(album: number): void {
      this.router.navigate(['/album', album]); 
  }
  
  //Añade a favoritos segun el usuario
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
