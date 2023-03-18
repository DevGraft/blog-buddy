package blogbuddy.searchengine.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("search/blog")
@RestController
public class BlogSearchApi {
    @GetMapping
    public void blogSearch() {

    }
}
