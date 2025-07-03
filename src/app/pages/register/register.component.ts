import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  email: string = '';
  role: string = 'VISITOR';

  successMessage: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

register() {
  const body = {
    username: this.username.trim(),
    password: this.password,
    email: this.email.trim(),
    role: this.role
  };

  this.http.post('http://localhost:8080/api/auth/register', body, { responseType: 'text' }).subscribe({
    next: (res) => {
      this.errorMessage = ''; // ✅ Clear previous error
      this.successMessage = res || 'Registered successfully. Please log in.';
      
      // Delay navigation slightly to let success message show (optional)
      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 1000);
    },
    error: (err) => {
      this.successMessage = ''; // ✅ Clear success
      if (err.status === 400 && typeof err.error === 'string') {
        this.errorMessage = err.error;
      } else {
        this.errorMessage = 'Something went wrong. Please try again.';
      }
    }
  });
}


  }
