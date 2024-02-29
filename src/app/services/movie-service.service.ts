import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Movie } from '../models/movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private moviesSubject = new BehaviorSubject<Movie[]>([]);
  movies$: Observable<Movie[]> = this.moviesSubject.asObservable();

  constructor(private http: HttpClient) {}

  fetchMovies(): void {
    this.http.get<any>('http://www.omdbapi.com/?apikey=51fd83b&s=movie').pipe(
      map(response => response.Search)
    ).subscribe(
      (movies: Movie[]) => {
        this.moviesSubject.next(movies);
      },
      error => {
        console.error('Error fetching movies:', error);
      }
    );
  }
}
