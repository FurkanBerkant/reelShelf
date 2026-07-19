package com.berkant.reelshelf.client;

import com.berkant.reelshelf.dto.MovieSearchResponse;
import com.berkant.reelshelf.dto.TmdbMovieDetailsResponse;
import com.berkant.reelshelf.dto.TmdbMovieSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TmdbMovieClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    @Value("${tmdb.api.image-base-url}")
    private String imageBaseUrl;

    public List<MovieSearchResponse> searchMovies(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Arama metni boş olamaz.");
        }

        RestClient restClient = restClientBuilder.baseUrl(baseUrl).build();

        TmdbMovieSearchResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .queryParam("language", "tr-TR")
                        .build())
                .retrieve()
                .body(TmdbMovieSearchResponse.class);

        if (response == null || response.results() == null) {
            return List.of();
        }

        return response.results()
                .stream()
                .map(this::toMovieSearchResponse)
                .toList();
    }

    public TmdbMovieDetailsResponse getMovieDetails(Long tmdbId) {
        if (tmdbId == null) {
            throw new IllegalArgumentException("TMDb film id boş olamaz.");
        }

        RestClient restClient = restClientBuilder.baseUrl(baseUrl).build();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{movieId}")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "tr-TR")
                        .build(tmdbId))
                .retrieve()
                .body(TmdbMovieDetailsResponse.class);
    }

    public String buildPosterUrl(String posterPath) {
        if (posterPath == null || posterPath.isBlank()) {
            return null;
        }

        return imageBaseUrl + posterPath;
    }

    private MovieSearchResponse toMovieSearchResponse(TmdbMovieDetailsResponse response) {
        return new MovieSearchResponse(
                response.id(),
                response.title(),
                response.releaseYear(),
                response.overview(),
                buildPosterUrl(response.posterPath()),
                response.originalLanguage(),
                response.voteAverage()
        );
    }
}