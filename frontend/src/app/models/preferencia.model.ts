export interface Preferencia {
    id: number;
    tipo: 'pelicula' | 'album' | 'pista';
    referenciaId:  number | string;
    fechaAgregada: string;
}
  