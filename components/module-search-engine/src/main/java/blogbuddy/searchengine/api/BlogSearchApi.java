package blogbuddy.searchengine.api;


import blogbuddy.searchengine.app.BlogSearchService;
import blogbuddy.searchengine.app.SearchPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("search/blog")
@RestController
public class BlogSearchApi {
    private final BlogSearchService blogSearchService;
    @GetMapping
    public void blogSearch(@RequestParam(name = "keyword", required = false) String keyword) {
        final SearchPostResponse searchPostResponse = blogSearchService.searchPost(keyword);
    }
}
