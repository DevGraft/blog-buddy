package blogbuddy.blog.api;


import blogbuddy.blog.app.SearchBlogResponse;
import blogbuddy.blog.app.SearchBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class BlogApi {
    private final SearchBlogService searchBlogService;

    @GetMapping("search/blog")
    public SearchBlogResponse searchBlog(@RequestParam(name = "keyword", required = false) String keyword,
                                         @RequestParam(name = "page", required = false) Integer page,
                                         @RequestParam(name = "size", required = false) Integer size,
                                         @RequestParam(name = "sort", required = false) String sort) {
        return searchBlogService.searchBlog(keyword, page, size, sort);
    }
}
