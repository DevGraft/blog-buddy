package blogbuddy.searchengine.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogPostFindRequest {
    private final String query;
}
