export interface AlbumResponse{
    id: number;
    name: string;
    artistId: number;
    artistName: string;
    year: number;
    spotifyId: string;
    image: string;
    tracks: TrackResponse[];
}

export interface AlbumRequest {
    name: string;
    artistId: number;
    artistName: string;
    year: number;
    spotifyId: string;
    image: string;
}


export interface TrackResponse{
    id: number;
    name: string;
    albumId: number;
    albumName: string;
    trackNumber: number;
    discNumber: number;
    explicit: boolean;
    durationMs: number;
    spotifyId: string;
}

export interface TrackRequest{
    name: string;
    albumId: number;
    albumName: string;
    trackNumber: number;
    discNumber: number;
    explicit: boolean;
    durationMs: number;
    spotifyId: string;
}