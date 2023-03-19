package blogbuddy.searchengine.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FindBlogPostRequest {
    private final String query;
    private final Integer page;
    private final Integer size;

    public static FindBlogPostRequest mapped(final String keyword, final Integer page, final Integer size) {
        return FindBlogPostRequest.builder()
                .query(keyword)
                .page(page)
                .size(size)
                .build();
    }
}
