export interface LoginRequestDTO {
  username: string;
  password: string;
}

export interface LoginResponseDTO {
  accessToken: string;
  tokenType: string;
  expiresIn: number
}
