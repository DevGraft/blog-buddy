package blogbuddy.searchengine.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BlogPostFindRequest {
    private final String query;

    public static BlogPostFindRequest mapped(final String keyword) {
        return BlogPostFindRequest.builder()
                .query(keyword)
                .build();
    }
}
