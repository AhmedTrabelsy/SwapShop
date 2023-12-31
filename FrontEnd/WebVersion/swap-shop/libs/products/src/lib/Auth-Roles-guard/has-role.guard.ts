import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthentificationService } from '../services/authentification.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HasRoleGuard implements CanActivate {

  constructor(private authService: AuthentificationService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    const requiredRole = route.data['userRole'] as string; // Get the required role from route data

    return this.authService.getUserInfo(sessionStorage.getItem('access_token')|| "").pipe(
      map((userInfo: any) => {
        const userRoles = userInfo.realm_access?.roles || []; // Get user roles from the token
        return userRoles.includes(requiredRole); // Check if the required role is present in the user's roles
      })
    );
  }
}
