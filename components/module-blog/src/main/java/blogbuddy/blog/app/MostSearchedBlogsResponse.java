package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogSearchHistoryByCountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class MostSearchedBlogsResponse {
    private final List<MostSearchedBlogItem> blogs;

    public static MostSearchedBlogsResponse mapped(Collection<? extends BlogSearchHistoryByCountDto> dtoCollection) {
        if (null == dtoCollection) {
            return new MostSearchedBlogsResponse(List.of());
        }
        final List<MostSearchedBlogItem> items = dtoCollection.stream()
                .map(dto -> new MostSearchedBlogItem(dto.getKeyword(), dto.getCount()))
                .toList();

        return new MostSearchedBlogsResponse(items);
    }
}
