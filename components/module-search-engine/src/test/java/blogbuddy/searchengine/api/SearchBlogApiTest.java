package blogbuddy.searchengine.api;

import blogbuddy.searchengine.app.GetBlogDocument;
import blogbuddy.searchengine.app.GetBlogMeta;
import blogbuddy.searchengine.app.GetBlogResponse;
import blogbuddy.searchengine.app.GetBlogService;
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
class SearchBlogApiTest {
    private MockMvc mockMvc;
    @InjectMocks
    private SearchBlogApi searchBlogApi;
    @Mock
    private GetBlogService mockGetBlogService;
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
        mockMvc = MockMvcBuilders.standaloneSetup(searchBlogApi).build();
    }

    @DisplayName("블로그 검색 정상 결과는 status=Ok(200)입니다.")
    @Test
    void getBlog_returnOkHttpStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("전달받은 Param을 서비스에 전달합니다.")
    @Test
    void getBlog_passesParamToService() throws Exception {
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

        Mockito.verify(mockGetBlogService, Mockito.times(1)).getBlog(keywordCaptor.capture(), pageCaptor.capture(), sizeCaptor.capture(), sortCaptor.capture());
        Assertions.assertThat(keywordCaptor.getValue()).isEqualTo(givenKeyword);
        Assertions.assertThat(pageCaptor.getValue()).isEqualTo(givenPage);
        Assertions.assertThat(sizeCaptor.getValue()).isEqualTo(givenSize);
        Assertions.assertThat(sortCaptor.getValue()).isEqualTo(givenSort);
    }
    @DisplayName("블로그 검색 결과는 반환됩니다.")
    @Test
    void getBlog_returnValue() throws Exception {
        final String givenKeyword = "Kakao Landing";
        final GetBlogMeta givenMeta = new GetBlogMeta(1, 1, true);
        final GetBlogDocument givenDocument = new GetBlogDocument("kakao-title", "kakao landing content", "url",
                "PCloud", "thumbnail", LocalDate.now());
        final GetBlogResponse givenResponse = new GetBlogResponse(givenMeta, List.of(givenDocument));
        BDDMockito.given(mockGetBlogService.getBlog(givenKeyword, null, null, null)).willReturn(givenResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("keyword", givenKeyword))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta", notNullValue(GetBlogMeta.class)))
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