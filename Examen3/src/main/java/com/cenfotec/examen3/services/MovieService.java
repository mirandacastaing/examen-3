package com.cenfotec.examen3.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenfotec.examen3.model.Movie;
import com.cenfotec.examen3.repository.MovieRepository;

@Service
public class MovieService {
	@Autowired
	MovieRepository movieRepo;

	public List<Movie> getAllMovies() {
		return this.movieRepo.findAll().stream().limit(this.movieRepo.findAll().size()).collect(Collectors.toList());
	}

	public List<Movie> getAllMovies(int count) {
		return this.movieRepo.findAll().stream().limit(count).collect(Collectors.toList());
	}

	public List<Movie> getAllMoviesByTitleLike(String title) {
		return this.movieRepo.findByTitleLike(title).stream().collect(Collectors.toList());
	}

	public List<Movie> getAllMoviesByYear(int year) {
		List<Movie> movies = new ArrayList<>();
		LocalDate release;

		for (Movie movie : getAllMovies()) {
			release = movie.getRelease().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

			if (release.getYear() == year)
				movies.add(movie);
		}

		return movies.stream().collect(Collectors.toList());
	}

	public Optional<Movie> getMovie(String id) {
		return this.movieRepo.findById(id);
	}

	public Movie createMovie(String title, double budget, double runtime, String language, Date release) {
		Movie movie = new Movie();

		movie.setTitle(title);
		movie.setBudget(budget);
		movie.setRuntime(runtime);
		movie.setLanguage(language);
		movie.setRelease(release);

		return this.movieRepo.save(movie);
	}

	public Movie updateMovie(String id, String title, double budget, double runtime, String language, Date release) {
		return this.movieRepo.findById(id).map(movie -> {
			movie.setTitle(title);
			movie.setBudget(budget);
			movie.setRuntime(runtime);
			movie.setLanguage(language);
			movie.setRelease(release);

			Movie updated = this.movieRepo.save(movie);

			return updated;
		}).orElse(null);
	}

	public Movie deleteMovie(String id) {
		this.movieRepo.findById(id).map(movie -> {
			this.movieRepo.deleteById(id);

			return null;
		});

		return null;
	}
}
