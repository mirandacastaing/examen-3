package com.cenfotec.examen3.mutation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cenfotec.examen3.model.Movie;
import com.cenfotec.examen3.services.MovieService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

@Component
public class MovieMutation implements GraphQLMutationResolver {
	@Autowired
	private MovieService movieService;

	public Movie createMovie(String title, double budget, double runtime, String language, Date release) {
		return this.movieService.createMovie(title, budget, runtime, language, release);
	}

	public Movie updateMovie(String id, String title, double budget, double runtime, String language, Date release) {
		return this.movieService.updateMovie(id, title, budget, runtime, language, release);
	}

	public Movie deleteMovie(String id) {
		return this.movieService.deleteMovie(id);
	}
}
