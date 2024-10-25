package com.example.demo.movies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.member.entity.Member;
import com.example.demo.movies.entity.Movies;

import jakarta.transaction.Transactional;

@Transactional //SQL작업결과 commit
public interface MoviesRepository extends JpaRepository<Movies, Long>   {

	List<Movies> findByTitleContainingOrderByPopularityDesc(String search);
	
}
