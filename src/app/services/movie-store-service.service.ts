import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { Movie } from '../models/movie';

@Injectable({
  providedIn: 'root'
})

  export class MovieStoreService {
    private moviesSubject = new BehaviorSubject<Movie[]>([]);
    movies$ = this.moviesSubject.asObservable();
  
    constructor() { }
  
    updateMovies(movies: Movie[]): void {
      this.moviesSubject.next(movies);
    }

    getMovieById(id: string): Observable<Movie | undefined> {
      return this.movies$.pipe(
        map(movies => movies.find(movie => movie.imdbID === id))
      );
    }
  }
  