package com.berkant.reelshelf.client;

import com.berkant.reelshelf.dto.MovieSearchResponse;
import com.berkant.reelshelf.dto.tmdb.TmdbMovieDetailsResponse;
import com.berkant.reelshelf.dto.tmdb.TmdbMovieSearchResponse;
import com.berkant.reelshelf.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TmdbMovieClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    @Value("${tmdb.api.image-base-url}")
    private String imageBaseUrl;

    public List<MovieSearchResponse> searchMovies(String query) {
        if (query == null || query.trim().length() < 2) {
            throw new IllegalArgumentException("Arama metni en az 2 karakter olmalıdır.");
        }

        ensureConfigured();

        TmdbMovieSearchResponse response;
        try {
            response = createClient().get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search/movie")
                            .queryParam("api_key", apiKey)
                            .queryParam("query", query.trim())
                            .queryParam("language", "tr-TR")
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, apiResponse) -> {
                        throw new ExternalApiException("TMDb şu anda arama isteğine yanıt veremiyor.");
                    })
                    .body(TmdbMovieSearchResponse.class);
        } catch (ExternalApiException exception) {
            throw exception;
        } catch (RestClientException exception) {
            log.error("TMDb arama iletişim hatası: {}", exception.getMessage());
            throw new ExternalApiException("TMDb servisine geçici olarak erişilemiyor.", exception);
        }

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

        ensureConfigured();

        try {
            return createClient().get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/movie/{movieId}")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "tr-TR")
                            .build(tmdbId))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, apiResponse) -> {
                        throw new ExternalApiException("TMDb film detayına şu anda erişilemiyor.");
                    })
                    .body(TmdbMovieDetailsResponse.class);
        } catch (ExternalApiException exception) {
            throw exception;
        } catch (RestClientException exception) {
            log.error("TMDb detay iletişim hatası: {}", exception.getMessage());
            throw new ExternalApiException("TMDb servisine geçici olarak erişilemiyor.", exception);
        }
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

    private RestClient createClient() {
        return restClientBuilder.clone().baseUrl(baseUrl).build();
    }

    private void ensureConfigured() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ExternalApiException("TMDb API anahtarı yapılandırılmamış.");
        }
    }
}
