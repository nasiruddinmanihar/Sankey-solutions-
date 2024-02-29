import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../services/movie-service.service';
import { MovieStoreService } from '../../services/movie-store-service.service';
import { Movie } from '../../models/movie';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  movies: Movie[] = [];

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.movieService.fetchMovies();
    this.movieService.movies$.subscribe(movies => {
      this.movies = movies;
    });
  }
 
}
