package com.example.demo.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.movies.entity.Movies;

public interface MoviesRepository extends JpaRepository<Movies, Long>   {

}
