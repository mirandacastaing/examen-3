package com.cenfotec.examen3.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cenfotec.examen3.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
	List<Movie> findByTitleLike(String title);

	List<Movie> findByBudgetBetween(double minimum, double maximum);

	List<Movie> findByRuntimeBetween(double minimum, double maximum);
}
