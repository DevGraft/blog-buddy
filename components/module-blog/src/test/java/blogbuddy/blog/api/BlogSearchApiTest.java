package blogbuddy.blog.api;

import blogbuddy.blog.app.BlogSearchDocument;
import blogbuddy.blog.app.BlogSearchMeta;
import blogbuddy.blog.app.BlogSearchResponse;
import blogbuddy.blog.app.BlogSearchService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 API")
class BlogSearchApiTest {
    private MockMvc mockMvc;
    @InjectMocks
    private BlogSearchApi blogSearchApi;
    @Mock
    private BlogSearchService mockBlogSearchService;
    @Captor
    private ArgumentCaptor<String> keywordCaptor;
    @Captor
    private ArgumentCaptor<Integer> pageCaptor;
    @Captor
    private ArgumentCaptor<Integer> sizeCaptor;
    @Captor
    private ArgumentCaptor<String> sortCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogSearchApi).build();
    }

    @DisplayName("블로그 검색 정상 결과는 status=Ok(200)입니다.")
    @Test
    void searchBlog_returnOkHttpStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("전달받은 Param을 서비스에 전달합니다.")
    @Test
    void searchBlog_passesParamToService() throws Exception {
        final String givenKeyword = "Kakao Landing";
        final Integer givenPage = 1;
        final Integer givenSize = 10;
        final String givenSort = "recency";
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog")
                .param("keyword", givenKeyword)
                .param("page", String.valueOf(givenPage))
                .param("size", String.valueOf(givenSize))
                .param("sort", givenSort)
        );

        Mockito.verify(mockBlogSearchService, Mockito.times(1)).searchBlog(keywordCaptor.capture(), pageCaptor.capture(), sizeCaptor.capture(), sortCaptor.capture());
        Assertions.assertThat(keywordCaptor.getValue()).isEqualTo(givenKeyword);
        Assertions.assertThat(pageCaptor.getValue()).isEqualTo(givenPage);
        Assertions.assertThat(sizeCaptor.getValue()).isEqualTo(givenSize);
        Assertions.assertThat(sortCaptor.getValue()).isEqualTo(givenSort);
    }
    @DisplayName("블로그 검색 결과는 반환됩니다.")
    @Test
    void searchBlog_returnValue() throws Exception {
        final String givenKeyword = "Kakao Landing";
        final BlogSearchMeta givenMeta = new BlogSearchMeta(1, 1, true);
        final BlogSearchDocument givenDocument = new BlogSearchDocument("kakao-title", "kakao landing content", "url",
                "PCloud", "thumbnail", LocalDate.now());
        final BlogSearchResponse givenResponse = new BlogSearchResponse(givenMeta, List.of(givenDocument));
        BDDMockito.given(mockBlogSearchService.searchBlog(givenKeyword, null, null, null)).willReturn(givenResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("keyword", givenKeyword))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta", notNullValue(BlogSearchMeta.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.totalCount").value(givenResponse.meta().totalCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.pageableCount").value(givenResponse.meta().pageableCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.isEnd").value(givenResponse.meta().isEnd()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].title").value(givenDocument.title()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].contents").value(givenDocument.contents()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].url").value(givenDocument.url()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].blogname").value(givenDocument.blogName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].thumbnail").value(givenDocument.thumbnail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].postDate").isNotEmpty())
        ;
    }
}