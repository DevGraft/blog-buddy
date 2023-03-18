package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.BlogPostFindResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchPostResponse {
    private final int totalCount;
    private final int pageableCount;
    private final boolean isEnd;
    private final List<SearchPostItem> documents;

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
