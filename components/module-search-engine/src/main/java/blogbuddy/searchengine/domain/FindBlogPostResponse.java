package blogbuddy.searchengine.domain;

import blogbuddy.kakaosearch.SearchBlogResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record FindBlogPostResponse(FindBlogPostMeta meta, List<FindBlogPostDocument> documents) {
    public static FindBlogPostResponse mapped(final SearchBlogResponse response) {
        final FindBlogPostMeta meta = FindBlogPostMeta.mapped(response.getMeta());
        final List<FindBlogPostDocument> documents = response.getDocuments().stream()
                .map(FindBlogPostDocument::mapped)
                .toList();
        return builder()
                .meta(meta)
                .documents(documents)
                .build();
    }
}
