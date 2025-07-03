import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  error = ''; 

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const body = { username: this.username, password: this.password };

    this.http.post<{ token: string }>('http://localhost:8080/api/auth/login', body).subscribe({
      next: (res) => {
        const token = res.token;
        localStorage.setItem('token', token);

        const decoded: any = jwtDecode(token);
        const role = decoded?.role || 'UNKNOWN';
        console.log('Logged in as:', role);

        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Login failed', err);
        this.error = 'Invalid credentials';
      }
    });
  }
}
