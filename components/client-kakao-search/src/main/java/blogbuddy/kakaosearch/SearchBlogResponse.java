package blogbuddy.kakaosearch;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchBlogResponse {
    private SearchBlogMeta meta;
    private List<SearchBlogDocument> documents;
}
