export interface MovieRequest{
    title: string;
    releaseDate: Date;
    voteAverage: number;
    adult: boolean;
    description: string;
    img: string
    homepage: string
    //Set<Long> genreIds
}

export interface MovieResponse{
    id: number;
    title: string;
    releaseDate: Date;
    voteAverage: number;
    adult: boolean;
    description: string;
    img: string
    homepage: string
    genres: GenreResponse[];
}

export interface GenreResponse {
  id: number;
  name: string;
}