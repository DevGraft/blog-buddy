package blogbuddy.blog.app;


import blogbuddy.blog.domain.FindBlogPostResponse;

import java.util.List;

public record BlogSearchResponse(BlogSearchMeta meta, List<BlogSearchDocument> documents) {
    public static BlogSearchResponse mapped(final FindBlogPostResponse response) {
        final BlogSearchMeta meta = BlogSearchMeta.mapped(response.meta());
        final List<BlogSearchDocument> documents = response.documents().stream()
                .map(BlogSearchDocument::mapped)
                .toList();

        return new BlogSearchResponse(meta, documents);
    }
}
