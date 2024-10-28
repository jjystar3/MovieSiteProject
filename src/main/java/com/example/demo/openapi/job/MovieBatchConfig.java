package com.example.demo.openapi.job;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.movies.entity.Movies;
import com.example.demo.movies.repository.MoviesRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Configuration
public class MovieBatchConfig {

    @Value("${tmdb.api.key}")
    private String serviceKey;

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;
    
    private List<String> allPagesData = new ArrayList<>();
    private List<Movies> moviesList = new ArrayList<>();

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private final String language = "ko-KR";
    private final String adult = "include_adult=false";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MovieBatchConfig() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public Job fetchMoviesJob() throws IOException {
        return new JobBuilder("FetchMoviesJob", jobRepository)
                .start(fetchMoviesStep())
                .next(parseMoviesStep())
                .next(saveMoviesStep())
                .build();
    }

    @Bean
    public Step fetchMoviesStep() {
        return new StepBuilder("Step1. Fetch Movies from TMDB API", jobRepository)
                .tasklet(fetchMoviesTasklet(), transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step parseMoviesStep() {
        return new StepBuilder("Step2. Parse Fetched Movies Data", jobRepository)
                .tasklet(parseMoviesTasklet(), transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step saveMoviesStep() {
        return new StepBuilder("Step3. Save Movies to Database", jobRepository)
                .tasklet(saveMoviesTasklet(), transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Tasklet fetchMoviesTasklet() {
        return (contribution, chunkContext) -> {
            int page = 1;

            // Fetch movies from the API page by page
            do {
                String url = "https://api.themoviedb.org/3/discover/movie?" + adult + "&language=" + language + "&page=" + page + "&sort_by=popularity.desc";
                String responseJson = makeApiRequest(url);

                if (responseJson == null || responseJson.isEmpty()) {
                    throw new IOException("Empty or invalid response from API.");
                }

                // Store each page's data separately in the list
                allPagesData.add(responseJson);
                page++;

                Root root = mapper.readValue(responseJson, Root.class);
                if (page > root.total_pages || page > 10) break;  // Limit to 10 pages or end if no more pages

            } while (true);
            
//          StepContext context = chunkContext.getStepContext();
//	        context.getStepExecution().getJobExecution().getExecutionContext().put("FetchedMovieData", allPagesData);
	        
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet parseMoviesTasklet() {
        return (contribution, chunkContext) -> {

//	        StepContext context = chunkContext.getStepContext();
//			Object apiString = context.getStepExecution().getJobExecution().getExecutionContext().get("FetchedMovieData");
//        	
//            @SuppressWarnings("unchecked")
//            List<String> fetchedData = (List<String>) apiString;
//
//            if (fetchedData == null) {
//                throw new IllegalStateException("FetchedMovieData is missing in the ExecutionContext.");
//            }

            for (String pageData : allPagesData) {
                // Parse each page's data
                Root root = mapper.readValue(pageData, Root.class);

                for (Result movieResult : root.results) {
                    // Fetch credits and videos
                    String movieID = String.valueOf(movieResult.id);
                    String creditsJson = getCredits(movieID);
                    String videosJson = getVideos(movieID);

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

                    String posterPath = (movieResult.poster_path != null) ? movieResult.poster_path : "/zr9Yel6jjlNe1O3xBnrndZDL3nC.jpg";
                    String backdropPath = (movieResult.backdrop_path != null) ? movieResult.backdrop_path : "/o9uMF84ZAGBqRxbliFCTgw0vQYv.jpg";
                    
                    // Get the first YouTube video link (if available)
                    String youtubeLink = rootVideos.results.stream()
                            .findFirst()
                            .map(result -> result.key)
                            .orElse("n5HbQ7vCvDY");

                    // Parse the release date
                    LocalDate releaseDate = null;
                    if (movieResult.release_date != null && !movieResult.release_date.trim().isEmpty()) {
                        releaseDate = LocalDate.parse(movieResult.release_date, formatter);
                    }

                    // Create Movies entity
                    Movies moviesEntity = Movies.builder()
                            .movieId(Long.valueOf(movieResult.id))
                            .popularity(movieResult.popularity)
                            .adult(movieResult.adult)
                            .title(movieResult.title)
                            .overview(movieResult.overview)
                            .posterPath(posterPath)
                            .backdropPath(backdropPath)
                            .videoPath(youtubeLink)
                            .releaseDate(releaseDate)
                            .directors(directorsString)
                            .actors(actorsString)
                            .build();

                    moviesList.add(moviesEntity);
                }
            }

//	        context.getStepExecution().getJobExecution().getExecutionContext().put("ParsedMoviesList", moviesList);
	        
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet saveMoviesTasklet() {
        return (contribution, chunkContext) -> {

//	        StepContext context = chunkContext.getStepContext();
//			Object jsonString = context.getStepExecution().getJobExecution().getExecutionContext().get("ParsedMoviesList");
//        	
//            @SuppressWarnings("unchecked")
//            List<Movies> moviesList = (List<Movies>) jsonString;

//            if (moviesList == null || moviesList.isEmpty()) {
//                throw new IllegalStateException("ParsedMoviesList is empty or missing in the ExecutionContext.");
//            }

            // Save the parsed movies to the database
            moviesRepository.saveAll(moviesList);

            return RepeatStatus.FINISHED;
        };
    }

    // Helper method for API requests
    private String makeApiRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + serviceKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Failed API request: " + response.code() + " - " + response.message());
            }
            return response.body().string();
        }
    }

    private String getCredits(String movieId) throws IOException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?language=" + language + "&" + adult;
        return makeApiRequest(url);
    }

    private String getVideos(String movieId) throws IOException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?language=" + language + "&" + adult;
        return makeApiRequest(url);
    }
}
