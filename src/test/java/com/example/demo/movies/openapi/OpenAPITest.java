package com.example.demo.movies.openapi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.movies.entity.Movies;
import com.example.demo.movies.repository.MoviesRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SpringBootTest
public class OpenAPITest {
	
	String serviceKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMjI4OWI2NDE1YWU3ZjIzYzg5NTRlYmIyMDQ3MmQ4MyIsIm5iZiI6MTcyOTIxODg2MS41MTgzOTQsInN1YiI6IjY3MTFjNmI5MDk3YzNkNzc2MGY4N2I2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.O2mqNzbYvHys73U1x7oUofcG-GIrJl271KAF0_M5vSY";

	@Autowired
	MoviesRepository moviesRepository;

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
	public void jsonToDto() throws IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		String movies = getMovies();
		Root root = null;
		root = mapper.readValue(movies, Root.class);
		
		for(int i=0;i<root.results.size();i++) {

			String movieID = String.valueOf(root.results.get(i).id);
			
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
			
			String directorsString = String.join(", ", directors);
			String actorsString = String.join(", ", actors);
			
			List<String> keys = rootVideos.results.stream()
													    .map(result -> result.key)
													    .collect(Collectors.toList());
			
			String youtubeLink = null;
	
			if(keys.size()>0) {
				String youtubeKey = keys.get(0);
				youtubeLink = "https://www.youtube.com/watch?v=" + youtubeKey;
			}
			
//			System.out.println("아이디: " + movieID);
//			System.out.println("제목: " + root.results.get(i).title);
//			System.out.println("줄거리: " + root.results.get(i).overview);
//			System.out.println("포스터경로: " + root.results.get(i).poster_path);
//			System.out.println("배경경로: " + root.results.get(i).backdrop_path);
//			System.out.println("영상경로: " + youtubeLink);
//			System.out.println("개봉일: " + root.results.get(i).release_date);
//			System.out.println("감독: " + directors);
//			System.out.println("주연배우: " + actors);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(root.results.get(i).release_date);
			
			Movies moviesEntity = Movies.builder()
									.movieId(Long.valueOf(root.results.get(i).id))
									.title(root.results.get(i).title)
									.overview(root.results.get(i).overview)
									.posterPath(root.results.get(i).poster_path)
									.backdropPath(root.results.get(i).backdrop_path)
									.videoPath(youtubeLink)
									.releaseDate(date)
									.directors(directorsString)
									.actors(actorsString)
									.build();
			moviesRepository.save(moviesEntity);
		}
		
		
		
	}
	
}
