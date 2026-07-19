package com.berkant.reelshelf.dto.googlebooks;

import java.util.List;

public record GoogleBooksSearchResponse(
        List<GoogleBooksVolumeResponse> items
) {}