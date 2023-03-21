package blogbuddy.kakaosearch;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 카카오 블로그 검색 API 결과를 담는 클래스.
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KakaoSearchBlogResponse {
    private KakaoSearchBlogMeta meta;
    private List<KakaoSearchBlogDocument> documents;
}
