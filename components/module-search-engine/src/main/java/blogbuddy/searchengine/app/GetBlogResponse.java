package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.FindBlogPostResponse;

import java.util.List;

public record GetBlogResponse(GetBlogMeta meta, List<GetBlogDocument> documents) {
    public static GetBlogResponse mapped(final FindBlogPostResponse response) {
        final GetBlogMeta meta = GetBlogMeta.mapped(response.meta());
        final List<GetBlogDocument> documents = response.documents().stream()
                .map(GetBlogDocument::mapped)
                .toList();

        return new GetBlogResponse(meta, documents);
    }
}
