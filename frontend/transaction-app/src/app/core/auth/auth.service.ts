import {inject, Injectable, signal} from '@angular/core';
import {Observable, tap} from 'rxjs';

import {
  LoginRequestDTO,
  LoginResponseDTO
} from '../models/auth.models';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl = environment.apiBaseAuthUrl;

  private readonly token = signal<string | null>(null);

  readonly isAuthenticated = () => this.token() !== null;

  login(credentials: LoginRequestDTO): Observable<LoginResponseDTO> {
    return this.http
      .post<LoginResponseDTO>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          this.token.set(response.accessToken);
        })
      );
  }

  getAccessToken(): string | null {
    return this.token();
  }

  logout(): void {
    this.token.set(null);
  }


}
