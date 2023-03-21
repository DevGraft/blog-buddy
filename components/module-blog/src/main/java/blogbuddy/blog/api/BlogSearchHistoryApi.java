package blogbuddy.blog.api;


import blogbuddy.blog.app.MostSearchedBlogsResponse;
import blogbuddy.blog.app.MostSearchedBlogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogSearchHistoryApi {
    private final MostSearchedBlogsService mostSearchedBlogsService;
    @GetMapping("search/most-searched-blogs")
    public MostSearchedBlogsResponse getMostSearchedBlogs() {
        return mostSearchedBlogsService.getMostSearchedBlogs();
    }
}
