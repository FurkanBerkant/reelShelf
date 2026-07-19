package com.berkant.reelshelf.client;

import com.berkant.reelshelf.dto.BookSearchResponse;
import com.berkant.reelshelf.dto.googlebooks.GoogleBooksSearchResponse;
import com.berkant.reelshelf.dto.googlebooks.GoogleBooksVolumeResponse;
import com.berkant.reelshelf.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleBooksClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${googlebooks.api.key:}")
    private String apiKey;

    @Value("${googlebooks.api.base-url}")
    private String baseUrl;

    public List<BookSearchResponse> searchBooks(String query) {
        if (query == null || query.isBlank()) {
            log.warn("Arama isteği başarısız: Query boş.");
            throw new IllegalArgumentException("Arama metni boş olamaz.");
        }

        try {
            RestClient restClient = restClientBuilder.baseUrl(baseUrl).build();
            GoogleBooksSearchResponse response = restClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("/volumes").queryParam("q", query).queryParam("maxResults", 20);
                        if (apiKey != null && !apiKey.isBlank()) uriBuilder.queryParam("key", apiKey);
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response1) -> {
                        log.error("Google Books API hatası: {} - {}", response1.getStatusCode(), response1.getStatusText());
                        throw new RuntimeException("Google Books API'sine erişilemedi.");
                    })
                    .body(GoogleBooksSearchResponse.class);

            if (response == null || response.items() == null) return List.of();
            return response.items().stream().map(this::toBookSearchResponse).toList();

        } catch (RestClientResponseException e) {
            log.error("API İletişim hatası: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Beklenmeyen hata: {}", e.getMessage(), e);
            throw e;
        }
    }

    public GoogleBooksVolumeResponse getBookDetails(String volumeId) {
        if (volumeId == null || volumeId.isBlank()) {
            log.warn("Kitap detay isteği başarısız: VolumeId boş.");
            throw new IllegalArgumentException("Google Books volume id boş olamaz.");
        }

        try {
            return restClientBuilder.baseUrl(baseUrl).build().get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("/volumes/{volumeId}");
                        if (apiKey != null && !apiKey.isBlank()) uriBuilder.queryParam("key", apiKey);
                        return uriBuilder.build(volumeId);
                    })
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        log.error("Kitap detayı çekilirken API hatası: {}", response.getStatusCode());
                        throw new RuntimeException("Google Books API detayı alınamadı.");
                    })
                    .body(GoogleBooksVolumeResponse.class);

        } catch (Exception e) {
            log.error("getBookDetails hata: {}", e.getMessage());
            throw e;
        }
    }

    private BookSearchResponse toBookSearchResponse(GoogleBooksVolumeResponse item) {
        var info = item.volumeInfo();
        if (info == null) {
            return new BookSearchResponse(item.id(), null, null, null, null, null, null, null);
        }

        return new BookSearchResponse(
                item.id(),
                info.title(),
                BookMapper.joinValues(info.authors()),
                BookMapper.extractYear(info.publishedDate()),
                info.description(),
                BookMapper.ensureHttps(info.imageLinks() != null ? info.imageLinks().thumbnail() : null),
                info.language(),
                info.averageRating()
        );
    }
}