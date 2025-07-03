import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-visitors',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './visitors.component.html',
  styleUrls: ['./visitors.component.css']
})
export class VisitorsComponent implements OnInit {
  visitors: any[] = [];
  name = '';
  email = '';
  phone = '';
  role = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded: any = jwtDecode(token);
      this.role = decoded?.role;

      // ✅ Load visitors only for allowed roles
      if (['ADMIN', 'RECEPTIONIST', 'GUARD'].includes(this.role)) {
        this.loadVisitors();
      }
    }
  }

  // ✅ Load all visitors
  loadVisitors() {
    this.http.get<any[]>('http://localhost:8080/api/visitors/get-all').subscribe({
      next: (data) => this.visitors = data,
      error: (err) => console.error('Error loading visitors', err)
    });
  }

  // ✅ Add visitor — Only ADMIN and RECEPTIONIST
  addVisitor() {
    if (!this.name || !this.email || !this.phone) {
      alert('All fields are required.');
      return;
    }

    const body = { name: this.name, email: this.email, phone: this.phone };
    this.http.post('http://localhost:8080/api/visitors/add', body).subscribe({
      next: () => {
        this.loadVisitors();
        this.name = '';
        this.email = '';
        this.phone = '';
      },
      error: (err) => console.error('Error adding visitor', err)
    });
  }
}
