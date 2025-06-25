import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PreferenciaService } from '../../services/Preferencia/preferencia.service';
import { AuthService } from '../../services/Autentificador/auth.service';
import { FavoriteAlbumsResponse, FavoriteMoviesResponse } from '../../models/favorite.model';
import { MusicService } from '../../services/Musica/music.service';
import { DurationPipe } from '../../pipes/duration.pipe';

@Component({
  selector: 'app-album-details',
  imports: [DurationPipe],
  templateUrl: './album-details.component.html',
  styleUrl: './album-details.component.css'
})
export class AlbumDetailsComponent implements OnInit{
  album: any;
  isLoggedIn = false;

  constructor(private route:ActivatedRoute, private musicService: MusicService, private preferenciaService: PreferenciaService,
    private authService:AuthService){}

  ngOnInit(): void {
      const albumId= this.route.snapshot.params['id'];
      this.loadAlbumDetails(albumId);
      this.isLoggedIn = this.authService.isLoggedIn();
  }

  loadAlbumDetails(albumId: number): void{
      this.musicService.getAlbumDetails(albumId).subscribe({
        next: (response)=>{
          this.album = response
        },
        error: (err)=>{
          console.error('Error al cargar el album', err);
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
