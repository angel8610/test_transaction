import {inject, Injectable, signal} from '@angular/core';
import {Observable, tap} from 'rxjs';

import {
  AuthUser,
  LoginRequest,
  LoginResponse
} from '../models/auth.models';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8081/api';

  private readonly token = signal<string | null>(null);

  private readonly currentUser = signal<AuthUser | null>(null);

  readonly isAuthenticated = () => this.token() !== null;

  readonly user = this.currentUser.asReadonly();

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.apiUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          console.log('Log')
          this.token.set(response.accessToken);
          //this.currentUser.set(response.user);
        })
      );
  }

  getAccessToken(): string | null {
    return this.token();
  }

  logout(): void {
    this.token.set(null);
    this.currentUser.set(null);
  }


}
