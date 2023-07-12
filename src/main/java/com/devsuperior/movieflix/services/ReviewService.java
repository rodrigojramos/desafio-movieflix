package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

import jakarta.validation.Valid;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional
	public ReviewDTO insert(@Valid ReviewDTO dto) {
		Review entity = new Review();
		entity.setId(dto.getId());
		entity.setText(dto.getText());
		entity.setUser(authService.authenticated());
		
		Movie movie = movieRepository.getReferenceById(dto.getMovieId());
		entity.setMovie(movie);
		
		entity = reviewRepository.save(entity);
		return new ReviewDTO(entity);
	}
	
}
