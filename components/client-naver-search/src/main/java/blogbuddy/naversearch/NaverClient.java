package blogbuddy.naversearch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "naver-client", url = "${client.naver.url}")
public interface NaverClient {

    @GetMapping("/v1/search/blog")
    SearchBlogResponse searchBlog(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "display", required = false) Integer display,
            @RequestParam(name = "start", required = false) Integer start,
            @RequestParam(name = "sort", required = false) String sort
    ) throws NaverClientException;
}
