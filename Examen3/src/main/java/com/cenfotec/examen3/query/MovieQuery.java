package com.cenfotec.examen3.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cenfotec.examen3.model.Movie;
import com.cenfotec.examen3.services.MovieService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class MovieQuery implements GraphQLQueryResolver {
	@Autowired
	private MovieService movieService;

	public List<Movie> getMovies() {
		return this.movieService.getAllMovies();
	}

	public List<Movie> getMoviesWithCount(int count) {
		return this.movieService.getAllMovies(count);
	}

	public List<Movie> getMoviesByTitleLike(String title) {
		return this.movieService.getAllMoviesByTitleLike(title);
	}

	public List<Movie> getMoviesByYear(int year) {
		return this.movieService.getAllMoviesByYear(year);
	}

	public Optional<Movie> getMovie(String id) {
		return this.movieService.getMovie(id);
	}
}
