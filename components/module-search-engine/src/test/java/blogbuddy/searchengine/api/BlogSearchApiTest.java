package blogbuddy.searchengine.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
}