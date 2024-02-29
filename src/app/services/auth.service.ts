import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isAuthenticated: boolean = false;
  private currentUser: any;

  constructor() {
    this.loadCurrentUser();
  }

  login(email: string, password: string): boolean {
    const userDataString = localStorage.getItem('userData');
    
    if (userDataString) {
      const userData = JSON.parse(userDataString);
   
      if (userData.email === email && userData.password === password) {
        this.isAuthenticated = true;
        console.log(userData);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
  

  logout(): void {
    this.isAuthenticated = false;

    this.currentUser = null;
    localStorage.removeItem('userData');
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }
  getCurrentUser(): any {
    console.log(this.currentUser)
    return this.currentUser;
    
  }
  private loadCurrentUser(): void {
    const userDataString = localStorage.getItem('userData');
    console.log(userDataString+" hello")
   
    if (userDataString) {
      if (userDataString === 'authenticated') {
        
        this.isAuthenticated = true;
      } else {
        try {
          this.currentUser = JSON.parse(userDataString);
          console.log(this.currentUser)

          this.isAuthenticated = true; 
        } catch (error) {
          console.error('Error parsing user data from localStorage:', error);
        }
      }
    }
  }
  }

