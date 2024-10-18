package com.example.demo.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.repository.MoviesRepository;

@Service
public class MoviesServiceImpl implements MoviesService {

	@Autowired
	MoviesRepository moviesRepository;

	@Override
	public Page<MoviesDTO> getList(int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoviesDTO read(int no) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void register(MoviesDTO dto) {
//		Movies entity = dtoToEntity(dto);
//		moviesRepository.save(entity);
//	}
	
}
