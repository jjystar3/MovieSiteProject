package com.example.demo.movies.openapi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	private final OkHttpClient client = new OkHttpClient();
	
	@Value("${tmdb.api.key}")
	private String serviceKey;
	String movieList = "popular"; //  now_playing  popular  top_rated  upcoming
	String language = "ko-KR"; //  en-US  ko-KR
	
	@Autowired
	MoviesRepository moviesRepository;

    private String makeApiRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + serviceKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    // Fetch movies with pagination
    public Root getMovies(int page) throws IOException {
        String url = "https://api.themoviedb.org/3/movie/" + movieList + "?language=" + language + "&page=" + page;
        String responseJson = makeApiRequest(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(responseJson, Root.class);  // Return the Root object that contains movie data
    }

    public String getCredits(String movieId) throws IOException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?language=" + language;
        return makeApiRequest(url);
    }

    public String getVideos(String movieId) throws IOException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?language=" + language;
        return makeApiRequest(url);
    }
	
	@Test
	public void jsonToDto() throws IOException, ParseException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    
		int page = 1;
        Root root;

        do {
            // Fetch the movies for the current page
            root = getMovies(page);

            // Process each movie in the results
            for (Result movieResult : root.results) {
                String movieID = String.valueOf(movieResult.id);

                // Fetch credits and videos concurrently
                CompletableFuture<String> creditsFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        return getCredits(movieID);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                CompletableFuture<String> videosFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        return getVideos(movieID);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                String creditsJson = creditsFuture.get();
                String videosJson = videosFuture.get();

                RootCredit rootCredit = mapper.readValue(creditsJson, RootCredit.class);
                RootVideos rootVideos = mapper.readValue(videosJson, RootVideos.class);

                // Collect directors and actors
                String directorsString = rootCredit.crew.stream()
                        .filter(c -> "Director".equals(c.job))
                        .map(x -> x.name)
                        .collect(Collectors.joining(", "));

                String actorsString = rootCredit.cast.stream()
                        .map(x -> x.name)
                        .collect(Collectors.joining(", "));

                // Get the first YouTube video link (if available)
                String youtubeLink = rootVideos.results.stream()
                        .findFirst()
                        .map(result -> "https://www.youtube.com/watch?v=" + result.key)
                        .orElse(null);

             // Check if release_date is not null or empty before parsing
                Date releaseDate = null;
                if (movieResult.release_date != null && !movieResult.release_date.trim().isEmpty()) {
                    try {
                        releaseDate = formatter.parse(movieResult.release_date);
                    } catch (ParseException e) {
                        // Handle the parsing error (e.g., log it or continue)
                        System.err.println("Failed to parse release date for movie: " + movieResult.title);
                        e.printStackTrace();
                    }
                }

                // Build and save the movie entity
                Movies moviesEntity = Movies.builder()
                        .movieId(Long.valueOf(movieResult.id))
                        .title(movieResult.title)
                        .overview(movieResult.overview)
                        .posterPath(movieResult.poster_path)
                        .backdropPath(movieResult.backdrop_path)
                        .videoPath(youtubeLink)
                        .releaseDate(releaseDate)
                        .directors(directorsString)
                        .actors(actorsString)
                        .build();

                moviesRepository.save(moviesEntity);
            }

            // Increment the page number
            page++;

        } while (page <= root.total_pages && page <= 10);  // Continue fetching until all pages are processed
	    
	}
}
