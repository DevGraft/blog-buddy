package blogbuddy.searchengine.api;


import blogbuddy.searchengine.app.GetBlogResponse;
import blogbuddy.searchengine.app.GetBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("search/blog")
@RestController
public class SearchBlogApi {
    private final GetBlogService getBlogService;

    @GetMapping
    public GetBlogResponse getBlog(@RequestParam(name = "keyword", required = false) String keyword,
                                   @RequestParam(name = "page", required = false) Integer page,
                                   @RequestParam(name = "size", required = false) Integer size) {
        return getBlogService.getBlog(keyword, page, size);
    }
}
