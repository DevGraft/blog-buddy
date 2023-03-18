package blogbuddy.kakaosearch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "kakao-client", url = "${client.kakao.url}")
public interface KakaoSearchClient {
    @GetMapping("/v2/search/blog")
    SearchBlogResponse searchBlog(@RequestParam(name = "query") String query,
                                  @RequestParam(name = "sort", required = false) String sort,
                                  @RequestParam(name = "page", required = false) Integer page,
                                  @RequestParam(name = "size", required = false) Integer size
    ) throws KakaoSearchException;
}
