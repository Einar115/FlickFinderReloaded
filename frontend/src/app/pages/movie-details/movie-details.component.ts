import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PeliculaService } from '../../services/Pelicula/pelicula.service';
import { PreferenciaService } from '../../services/Preferencia/preferencia.service';
import { AuthService } from '../../services/Autentificador/auth.service';
import { FavoriteAlbumsResponse, FavoriteMoviesResponse } from '../../models/favorite.model';
import { ReleaseDatePipe } from "../../pipes/releaseDate.pipe";

@Component({
  selector: 'app-movie-details',
  imports: [ReleaseDatePipe],
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.css'
})
export class MovieDetailsComponent implements OnInit{
  movie: any;
  isLoggedIn = false;

  constructor(private route: ActivatedRoute, private peliculaService: PeliculaService, private preferenciaService: PreferenciaService,
    private authService: AuthService){}

  ngOnInit(): void {
      const movieId= this.route.snapshot.params['id'];
      this.loadMoviesDetails(movieId);
      this.isLoggedIn = this.authService.isLoggedIn();
  }
  
  loadMoviesDetails(movieId:number): void{
    this.peliculaService.getMovieDetails(movieId).subscribe({
      next: (response)=>{
        this.movie = response
      },
      error: (err)=>{
        console.error('Error al cargar la pelicula', err);
      }
    })
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
