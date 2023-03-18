package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.FindBlogPostResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetBlogResponse(GetBlogMeta meta, List<GetBlogDocument> documents) {
    public static GetBlogResponse mapped(final FindBlogPostResponse response) {
        final GetBlogMeta meta = GetBlogMeta.mapped(response.meta());
        final List<GetBlogDocument> documents = response.documents().stream()
                .map(GetBlogDocument::mapped)
                .toList();

        return builder()
                .meta(meta)
                .documents(documents)
                .build();
    }
}
