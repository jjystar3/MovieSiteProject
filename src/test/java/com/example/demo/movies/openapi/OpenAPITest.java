package com.example.demo.movies.openapi;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SpringBootTest
public class OpenAPITest {
	
	String serviceKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMjI4OWI2NDE1YWU3ZjIzYzg5NTRlYmIyMDQ3MmQ4MyIsIm5iZiI6MTcyOTIxODg2MS41MTgzOTQsInN1YiI6IjY3MTFjNmI5MDk3YzNkNzc2MGY4N2I2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.O2mqNzbYvHys73U1x7oUofcG-GIrJl271KAF0_M5vSY";
	
	public String getMovies() throws IOException {
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://api.themoviedb.org/3/movie/top_rated?language=ko-KR&page=1&region=ko")
		  .get()
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer " + serviceKey)
		  .build();

		Response response = client.newCall(request).execute();
		
		return response.body().string();
	}
	
	public String getCredits(String movieId) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://api.themoviedb.org/3/movie/" + movieId + "/credits?language=ko-KR")
		  .get()
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer " + serviceKey)
		  .build();

		Response response = client.newCall(request).execute();
		
		return response.body().string();
	}
	
	public String getVideos(String movieId) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://api.themoviedb.org/3/movie/" + movieId + "/videos?language=ko-KR")
		  .get()
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer " + serviceKey)
		  .build();

		Response response = client.newCall(request).execute();
		
		return response.body().string();
	}
	
	@Test
	public void jsonToDto() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		String movies = getMovies();
		Root root = null;
		root = mapper.readValue(movies, Root.class);
		
		String movieID = String.valueOf(root.results.get(0).id);
		
		String credits = getCredits(movieID);
		RootCredit rootCredit = null;
		rootCredit = mapper.readValue(credits, RootCredit.class);

		String videos = getVideos(movieID);
		RootVideos rootVideos = null;
		rootVideos = mapper.readValue(videos, RootVideos.class);
		
		List<String> directors = rootCredit.crew.stream()
													.filter(c -> c.job.equals("Director"))
													.map(x -> x.name)
													.collect(Collectors.toList());

		List<String> actors = rootCredit.cast.stream()
													.map(x -> x.name)
													.collect(Collectors.toList());
		
		System.out.println("전체 결과: " + root.results);

		System.out.println("아이디: " + movieID);
		System.out.println("제목: " + root.results.get(0).title);
		System.out.println("줄거리: " + root.results.get(0).overview);
		System.out.println("포스터경로: " + root.results.get(0).poster_path);
		System.out.println("배경경로: " + root.results.get(0).backdrop_path);
		System.out.println("영상경로: " + rootVideos.results);
		System.out.println("개봉일: " + root.results.get(0).release_date);
		System.out.println("감독: " + directors);
		System.out.println("주연배우: " + actors);
	}
	
}
