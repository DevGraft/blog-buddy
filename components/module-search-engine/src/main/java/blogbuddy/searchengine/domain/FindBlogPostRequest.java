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

    public static FindBlogPostRequest mapped(final String keyword) {
        return FindBlogPostRequest.builder()
                .query(keyword)
                .build();
    }
}
