package blogbuddy.blog.infra;

import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.blog.domain.FindBlogPostResponse;
import blogbuddy.blog.domain.FindBlogPostService;
import blogbuddy.kakaosearch.KakaoClient;
import blogbuddy.kakaosearch.KakaoClientException;
import blogbuddy.kakaosearch.KakaoSearchBlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class KakaoFindBlogPostService implements FindBlogPostService {
    private final KakaoClient kakaoClient;
    @Override
    public FindBlogPostResponse findBlog(FindBlogPostRequest request) throws KakaoClientException {
        final KakaoSearchBlogResponse kakaoSearchBlogResponse = kakaoClient.searchBlog(request.getQuery(), request.getSort(), request.getPage(), request.getSize());
        return FindBlogPostResponseMapper.mapped(kakaoSearchBlogResponse);
    }
}
