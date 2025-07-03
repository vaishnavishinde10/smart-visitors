import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-appointments',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {
  role: string = '';
  appointments: any[] = [];

  // Form fields (used by Visitor only)
  visitorId: string = '';
  hostId: string = '';
  scheduledAt: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded: any = jwtDecode(token);
      this.role = decoded?.role;

      if (['ADMIN', 'RECEPTIONIST', 'HOST'].includes(this.role)) {
        this.loadAppointments();
      }
    }
  }

  loadAppointments() {
    this.http.get<any[]>('http://localhost:8080/api/appointments/all').subscribe({
      next: (data) => this.appointments = data,
      error: (err) => console.error('Error loading appointments', err)
    });
  }

  createAppointment() {
    if (!this.visitorId || !this.hostId || !this.scheduledAt) {
      alert('All fields are required');
      return;
    }

    const body = {
      visitorId: Number(this.visitorId),
      hostId: Number(this.hostId),
      scheduledAt: this.scheduledAt
    };

    this.http.post('http://localhost:8080/api/appointments', body).subscribe({
      next: () => {
        alert('Appointment requested!');
        this.visitorId = '';
        this.hostId = '';
        this.scheduledAt = '';
      },
      error: (err) => {
        console.error('Error creating appointment', err);
        alert('Failed to request appointment');
      }
    });
  }
}
