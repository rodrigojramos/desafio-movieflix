package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		try {
				Movie movie = movieRepository.getReferenceById(id);
				return new MovieDetailsDTO(movie);
		}
		catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllPaged(Long genreId, PageRequest pageRequest) {
		Genre genre = (genreId == 0) ? null : genreRepository.getReferenceById(genreId);
		Page<Movie> page = movieRepository.findByGenre(genre, pageRequest);
		return page.map(x -> new MovieCardDTO(x));
	}

}
