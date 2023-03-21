package blogbuddy.blog.app;


import blogbuddy.blog.domain.FindBlogPostResponse;

import java.util.List;

public record SearchBlogResponse(SearchBlogMeta meta, List<SearchBlogDocument> documents) {
    public static SearchBlogResponse mapped(final FindBlogPostResponse response) {
        final SearchBlogMeta meta = SearchBlogMeta.mapped(response.meta());
        final List<SearchBlogDocument> documents = response.documents().stream()
                .map(SearchBlogDocument::mapped)
                .toList();

        return new SearchBlogResponse(meta, documents);
    }
}
