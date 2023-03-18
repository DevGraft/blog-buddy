package blogbuddy.kakaosearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchBlogDocument {
    private String title;
    private String contents;
    private String url;
    @JsonProperty("blogname")
    private String blogName;
    private String thumbnail;
    private String datetime;
}
