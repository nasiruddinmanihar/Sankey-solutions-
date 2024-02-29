import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css',
})
export class RegistrationComponent {

 
  username: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  registrationSuccess: boolean = false;

  constructor(private router: Router) {
    console.log("registration class loaded")
  }

  // Method to handle form submission
  onSubmit(): void {
    // Validate form fields
    if (!this.validateForm()) {
      return;
    }

    const userData = {
      username: this.username,
      email: this.email,
      password: this.password,
      confirmpassword: this.confirmPassword,
    };
    localStorage.setItem('userData', JSON.stringify(userData));

    this.registrationSuccess = true;

    this.username = '';
    this.email = '';
    this.password = '';
    this.confirmPassword = '';

    
  }

  private validateForm(): boolean {
    if (!this.username) {
      alert('Please provide a username.');
      return false;
    }

    if (!this.email) {
      alert('Please provide an email.');
      return false;
    } else if (!this.isValidEmail(this.email)) {
      alert('Please provide a valid email.');
      return false;
    }

    if (!this.password) {
      alert('Please provide a password.');
      return false;
    } else if (this.password.length < 6) {
      alert('Password must be at least 6 characters long.');
      return false;
    }

    if (!this.confirmPassword) {
      alert('Please confirm your password.');
      return false;
    } else if (this.password !== this.confirmPassword) {
      alert('Passwords do not match.');
      return false;
    }

    return true;
  }
  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }
  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }
}
