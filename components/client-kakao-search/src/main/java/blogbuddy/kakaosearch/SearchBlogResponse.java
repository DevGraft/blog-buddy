package blogbuddy.kakaosearch;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchBlogResponse {
    @JsonProperty(namespace = "meta")
    private SearchBlogMeta meta;
    private List<SearchBlogDocument> documents;
}
