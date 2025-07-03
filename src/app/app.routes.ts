import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { VisitorsComponent } from './pages/visitors/visitors.component';
import { AppointmentsComponent } from './pages/appointments/appointments.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard],
    data: { roles: ['ADMIN', 'RECEPTIONIST', 'GUARD', 'VISITOR', 'HOST'] }
  },
  {
    path: 'visitors',
    component: VisitorsComponent,
    canActivate: [authGuard],
    data: { roles: ['ADMIN', 'RECEPTIONIST', 'GUARD'] }
  },
  {
    path: 'visitors/add',
    component: VisitorsComponent,
    canActivate: [authGuard],
    data: { roles: ['ADMIN', 'RECEPTIONIST'] }
  },
  {
    path: 'appointments',
    component: AppointmentsComponent,
    canActivate: [authGuard],
    data: { roles: ['ADMIN', 'RECEPTIONIST', 'HOST', 'VISITOR'] }
  }
];
