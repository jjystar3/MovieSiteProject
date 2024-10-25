package com.example.demo.movies.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.movies.entity.Movies;

import jakarta.transaction.Transactional;

@Transactional //SQL작업결과 commit
public interface MoviesRepository extends JpaRepository<Movies, Long>   {

}
