import { Component } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  
})
export class NavbarComponent {

  constructor(private router: Router) { }

  checkLogin(): void {
    const isAuthenticated = localStorage.getItem('userData') === 'authenticated';
    if (!isAuthenticated) {
      alert('Please register or log in first.');
      return;
    }
    
    this.router.navigate(['/home']);
  }
}
