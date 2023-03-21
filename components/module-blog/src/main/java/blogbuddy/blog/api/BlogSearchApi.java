package blogbuddy.blog.api;


import blogbuddy.blog.app.BlogSearchResponse;
import blogbuddy.blog.app.BlogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class BlogSearchApi {
    private final BlogSearchService blogSearchService;

    @GetMapping("search/blog")
    public BlogSearchResponse searchBlog(@RequestParam(name = "keyword", required = false) String keyword,
                                         @RequestParam(name = "page", required = false) Integer page,
                                         @RequestParam(name = "size", required = false) Integer size,
                                         @RequestParam(name = "sort", required = false) String sort) {
        return blogSearchService.searchBlog(keyword, page, size, sort);
    }
}
