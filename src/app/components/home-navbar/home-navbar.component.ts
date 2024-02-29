import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-navbar',
  templateUrl: './home-navbar.component.html',
  styleUrl: './home-navbar.component.css'
})
export class HomeNavbarComponent implements OnInit{
  
  currentUser: any;

  constructor(private authService: AuthService, private router: Router) {
    console.log("homenav bar loaded")
   }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    console.log(this.currentUser)
  }


  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
