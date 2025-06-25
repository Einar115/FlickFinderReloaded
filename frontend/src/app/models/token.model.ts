export interface RefreshTokenRequest {
  refreshToken: string;
}

export interface TokenRequest {
  token: string;
}

export interface TokenValidationResponse {
  valid: boolean;
  userId: string;
  username: string;
}

