package blogbuddy.blog.infra;

import blogbuddy.blog.domain.FindBlogPostDocument;
import blogbuddy.blog.domain.FindBlogPostMeta;
import blogbuddy.blog.domain.FindBlogPostResponse;
import blogbuddy.kakaosearch.KakaoSearchBlogResponse;
import blogbuddy.naversearch.NaverSearchBlogResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class FindBlogPostResponseMapper {
    public static FindBlogPostResponse mapped(final KakaoSearchBlogResponse response) {
        final FindBlogPostMeta meta = new FindBlogPostMeta(
                response.getMeta().getTotalCount(),
                response.getMeta().getPageableCount(),
                response.getMeta().isEnd());

        final List<FindBlogPostDocument> documents = response.getDocuments().stream()
                .map(doc -> new FindBlogPostDocument(doc.getTitle(), doc.getContents(), doc.getUrl(), doc.getBlogName(), doc.getThumbnail(), doc.getDatetime().toLocalDateTime()))
                .toList();

        return new FindBlogPostResponse(meta, documents);
    }

    public static FindBlogPostResponse mapped(final NaverSearchBlogResponse response) {
        int pageableCount = 0;
        if (0 != response.getTotal()) {
            pageableCount = (response.getTotal() + response.getDisplay() - 1) / response.getDisplay();
        }

        final FindBlogPostMeta meta = new FindBlogPostMeta(
                response.getTotal(),
                pageableCount,
                pageableCount <= response.getStart()
        );

        final List<FindBlogPostDocument> documents = response.getItems().stream()
                .map(item -> new FindBlogPostDocument(item.getTitle(), item.getDescription(), item.getLink(), item.getBloggerName(), null, item.getPostdate().atTime(LocalTime.MIN)))
                .toList();

        return new FindBlogPostResponse(meta, documents);
    }
}
