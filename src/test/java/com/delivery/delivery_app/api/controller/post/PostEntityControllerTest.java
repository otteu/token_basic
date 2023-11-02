package com.delivery.delivery_app.api.controller.post;

import com.delivery.delivery_app.api.controller.post.request.PostCreateRequest;
import com.delivery.delivery_app.api.controller.post.request.PostModifyRequest;
import com.delivery.delivery_app.api.exception.common.CustomApiException;
import com.delivery.delivery_app.api.exception.common.ErrorCode;
import com.delivery.delivery_app.api.service.post.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;


    @DisplayName("포스트 작성 합니다.")
    @WithMockUser
    @Test
    void post_write() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body)))
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 하지 않고 포스트 작성 합니다.")
    @WithAnonymousUser
    @Test
    void no_login_post_write() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body)))
                ).andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_TOKEN.getStatus().value()));
    }

    @DisplayName("포스트 수정 합니다.")
    @WithMockUser
    @Test
    void post_modify() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(put("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 하지 않고 포스트 수정 합니다.")
    @WithAnonymousUser
    @Test
    void no_login_post_modify() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("본인이 작성한 포스트가 아닙니다.")
    @WithMockUser
    @Test
    void diff_login_post_modify() throws Exception {

        String title = "title";
        String body = "body";

        doThrow(new CustomApiException(ErrorCode.INVALID_PERMISSION)).when(postService).modify(eq(title), eq(body), any(), eq(1L));

        mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("수정하려는 포스트가 없습니다.")
    @WithMockUser
    @Test
    void not_find_post_modify() throws Exception {

        String title = "title";
        String body = "body";

        doThrow(new CustomApiException(ErrorCode.POST_NOT_FOUND)).when(postService).modify(eq(title), eq(body), any(), eq(1L));

        mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("포스트 삭제 합니다.")
    @WithMockUser
    @Test
    void post_delete() throws Exception {
        mockMvc.perform(delete("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("포스트 삭제시 로그인 하지 않아 오류가 발생합니다.")
    @WithAnonymousUser
    @Test
    void no_login_post_delete() throws Exception {
        mockMvc.perform(delete("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("포스트 삭제시 작성자와 달라 오류가 발생합니다.")
    @WithMockUser
    @Test
    void not_metcher_account_post_delete() throws Exception {
        // mocking
        doThrow(new CustomApiException(ErrorCode.INVALID_PERMISSION)).when(postService).delete(any(), any());

        mockMvc.perform(delete("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("포스트 삭제시 존재하지 포스트 삭제 요청시 오류가 발생합니다.")
    @WithMockUser
    @Test
    void not_find_post_delete() throws Exception {
        // mocking
        doThrow(new CustomApiException(ErrorCode.POST_NOT_FOUND)).when(postService).delete(any(), any());

        mockMvc.perform(delete("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }


}



































