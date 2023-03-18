package blogbuddy.searchengine.api;

import blogbuddy.searchengine.app.BlogSearchService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


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
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogSearchApi).build();
    }

    @DisplayName("블로그 검색 정상 결과는 status=Ok(200)입니다.")
    @Test
    void blogSearch_returnOkHttpStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("전달받은 Param을 서비스에 전달합니다.")
    @Test
    void blogSearch_passesParamToService() throws Exception {
        final String givenKeyword = "Kakao Landing";

        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("keyword", givenKeyword));

        Mockito.verify(mockBlogSearchService, Mockito.times(1)).searchPost(keywordCaptor.capture());
        Assertions.assertThat(keywordCaptor.getValue()).isEqualTo(givenKeyword);
    }

    @DisplayName("블로그 검색 결과는 반환됩니다.")
    @Test
    void blogSearch_returnValue() throws Exception {
        final String givenKeyword = "Kakao Landing";

        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("keyword", givenKeyword));


    }
}