export interface FavoriteMoviesResponse {
  favoritesMoviesId: number;
  userId: number;
  movieId: number;
  addedAt: string; // o Date si prefieres convertirlo
}

export interface FavoriteAlbumsResponse {
  favoritesAlbumId: number;
  userId: number;
  albumId: number;
  addedAt: string; // o Date si prefieres convertirlo
}

export interface FavoriteMoviesRequest {
  userId: number;
  movieId: number;
}

export interface FavoriteAlbumsRequest {
  userId: number;
  albumId: number;
}