import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  email: string = '';
  password: string = '';
  loginError: string = '';

  constructor(private authService: AuthService, private router: Router) {
    console.log("login class loaded")
  }

  onSubmit(): void {
    const isLoggedIn = this.authService.login(this.email, this.password);
    if (isLoggedIn) {
      this.router.navigate(['/home']);
    } else {
      this.loginError = 'Invalid email or password';
    }
  }
}