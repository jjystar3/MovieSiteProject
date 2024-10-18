package com.example.demo.movies.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.movies.entity.Movies;

@SpringBootTest
public class MoviesRepositoryTest {

	@Autowired
	MoviesRepository moviesRepository;

	@Test
	public void 영화등록() {
		Movies movies = Movies.builder()
								.movieId(123)
								.title("영화1")
								.build();
		moviesRepository.save(movies);
		
		Movies movies2 = Movies.builder()
								.movieId(234)
								.title("영화2")
								.build();
		moviesRepository.save(movies2);
	}
	
	@Test
	public void 영화목록조회() {
		List<Movies> list = moviesRepository.findAll();
		for(Movies movies : list) {
			System.out.println(movies);
		}
	}

	@Test
	public void 영화단건조회() {
		Optional<Movies> result = moviesRepository.findById(123);
		if(result.isPresent()) {
			Movies movies = result.get();
			System.out.println(movies);
		}
	}

	@Test
	public void 영화수정() {
		Optional<Movies> result = moviesRepository.findById(234);
		if(result.isPresent()) {
			Movies movies = result.get();
			// 일부 내용 변경
			movies.setTitle("영화 제목 수정");;
			// 데이터 업데이트
			moviesRepository.save(movies);
		}
	}
	
	@Test
	public void 영화삭제() {
		moviesRepository.deleteById(123);
	}

}
