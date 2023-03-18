package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.BlogPostFindResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record SearchPostResponse(int totalCount, int pageableCount, boolean isEnd, List<SearchPostItem> documents) {
    public static SearchPostResponse mapped(final BlogPostFindResponse response) {
        List<SearchPostItem> documents = response.getDocuments().stream()
                .map(SearchPostItem::mapped)
                .toList();

        return builder()
                .totalCount(response.getTotalCount())
                .pageableCount(response.getPageableCount())
                .isEnd(response.isEnd())
                .documents(documents)
                .build();
    }
}
