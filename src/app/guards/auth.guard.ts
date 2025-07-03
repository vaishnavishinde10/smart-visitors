import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode'; 

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (!token) {
    router.navigate(['/login']);
    return false;
  }

  try {
    const decoded: any = jwtDecode(token);
    const userRole: string = decoded?.role;

    const allowedRoles = route.data?.['roles'] || [];

    // ✅ If no roles are specified or user role is included in allowedRoles
    if (allowedRoles.length === 0 || allowedRoles.includes(userRole)) {
      return true;
    } else {
      alert('Access denied!');
      router.navigate(['/login']);
      return false;
    }
  } catch (e) {
    router.navigate(['/login']);
    return false;
  }
};
