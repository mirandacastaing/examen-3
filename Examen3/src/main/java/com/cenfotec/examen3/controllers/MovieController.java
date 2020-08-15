package com.cenfotec.examen3.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cenfotec.examen3.model.Movie;
import com.cenfotec.examen3.repository.MovieRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping({ "/movies" })
@Api(produces = "application/json", value = "Operations pertaining to movies management in the application")
public class MovieController {
	@Autowired
	private MovieRepository repository;

	MovieController(MovieRepository movieRepository) {
		this.repository = movieRepository;
	}

	@GetMapping
	@ApiOperation(value = "View all movies", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "You successfully retrieved all movies"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public List<Movie> findAll() {
		return repository.findAll();
	}

	@GetMapping(path = { "/{id}" })
	@ApiOperation(value = "Retrieve specific movie with the supplied movie id", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "You successfully retrieved the movie with the supplied id"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<Movie> findById(@PathVariable("id") String id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(params = { "title" })
	@ApiOperation(value = "Find movies by similar titles to the supplied one", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "You successfully retrieved movies with similar titles"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public List<Movie> findByTitleLike(@RequestParam("title") String title) {
		return repository.findByTitleLike(title);
	}

	@GetMapping(params = { "minimum_budget", "maximum_budget" })
	@ApiOperation(value = "Find movies by the supplied budget range", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "You successfully retrieved movies within the supplied budget range"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public List<Movie> findByBudgetRange(@RequestParam("minimum_budget") double minimum,
			@RequestParam("maximum_budget") double maximum) {
		return repository.findByBudgetBetween(minimum, maximum);
	}

	@GetMapping(params = { "minimum_runtime", "maximum_runtime" })
	@ApiOperation(value = "Find movies by the supplied runtime range", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "You successfully retrieved movies within the supplied runtime range"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public List<Movie> findByRuntimeRange(@RequestParam("minimum_runtime") double minimum,
			@RequestParam("maximum_runtime") double maximum) {
		return repository.findByRuntimeBetween(minimum, maximum);
	}

	@GetMapping(params = { "day", "month" })
	@ApiOperation(value = "Find movies released on the supplied day", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "You successfully retrieved movies released on the supplied day"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public List<Movie> findByDay(@RequestParam("day") int day, @RequestParam("month") int month) {
		List<Movie> movies = new ArrayList<>();
		LocalDate release;

		for (Movie movie : findAll()) {
			release = movie.getRelease().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

			if (release.getDayOfMonth() == day && release.getMonthValue() == month)
				movies.add(movie);
		}

		return movies;
	}

	@GetMapping(params = { "day", "month", "year" })
	@ApiOperation(value = "Find movies released on the supplied date", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "You successfully retrieved movies released on the supplied date"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public List<Movie> findByDate(@RequestParam("day") int day, @RequestParam("month") int month,
			@RequestParam("year") int year) {
		List<Movie> movies = new ArrayList<>();
		LocalDate release;

		for (Movie movie : findByDay(day, month)) {
			release = movie.getRelease().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

			if (release.getYear() == year)
				movies.add(movie);
		}

		return movies;
	}

	@GetMapping(params = { "year" })
	@ApiOperation(value = "Find movies released in the supplied year", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "You successfully retrieved movies released in the supplied year"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public List<Movie> findByYear(@RequestParam("year") int year) {
		List<Movie> movies = new ArrayList<>();
		LocalDate release;

		for (Movie movie : findAll()) {
			release = movie.getRelease().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

			if (release.getYear() == year)
				movies.add(movie);
		}

		return movies;
	}

	@PostMapping
	@ApiOperation(value = "Create a new movie", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "You successfully created a new movie"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
		try {
			return ResponseEntity.ok().body(repository.save(movie));
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Update movie information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "You successfully updated the movie's information"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<Movie> update(@PathVariable("id") String id, @RequestBody Movie movie) {
		return repository.findById(id).map(record -> {
			record.setTitle(movie.getTitle());
			record.setBudget(movie.getBudget());
			record.setRuntime(movie.getRuntime());
			record.setLanguage(movie.getLanguage());
			record.setRelease(movie.getRelease());

			Movie updated = repository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	@ApiOperation(value = "Delete specific movie with the supplied movie id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "You successfully deleted the specific movie"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Access to the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);

			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
