package blogbuddy.naversearch;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchBlogResponse {
    private Integer total;
    private Integer start;
    private Integer display;
    private String lastBuildDate;
    private List<SearchBlogItem> items;
}
