package com.delivery.delivery_app.api.service.post;

import com.delivery.delivery_app.api.exception.common.CustomApiException;
import com.delivery.delivery_app.api.exception.common.ErrorCode;
import com.delivery.delivery_app.api.fixture.AccountFixture;
import com.delivery.delivery_app.api.fixture.PostFixture;
import com.delivery.delivery_app.api.repository.account.AccountRepository;
import com.delivery.delivery_app.api.repository.post.PostRepository;
import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostEntityServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private AccountRepository accountRepository;

    @DisplayName("포스트 작성하였습니다.")
    @Test
    void post_write_success() {
        String title = "title";
        String body = "body";
        String nickname = "nickname";

        /// mocking
        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(mock(AccountEntity.class)));
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));


        Assertions.assertDoesNotThrow(() -> postService.create(title, body, nickname));
    }

    @DisplayName("로그인 없이 작성면 오류가 발생합니다.")
    @Test
    void no_login_post_write_fail() {
        String title = "title";
        String body = "body";
        String nickname = "nickname";

        /// mocking
        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        CustomApiException e = assertThrows(CustomApiException.class, () -> postService.create(title, body, nickname));
        Assertions.assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, e.getErrorCode());
    }

    @DisplayName("포스트 수정 하였습니다.")
    @Test
    void post_modify_success() {
        String title = "title";
        String body = "body";
        String nickname = "nickname";
        Long postId = 1L;


        PostEntity postEntity = PostFixture.get(nickname, postId, 1L);
        AccountEntity accountEntity = postEntity.getAccountEntity();
        /// mocking

        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));


        Assertions.assertDoesNotThrow(() -> postService.modify(title, body, nickname, postId));
    }

    @DisplayName("포스트가 존재 하지 않아서 에러가 발생하였습니다.")
    @Test
    void post_not_find_exception() {
        String title = "title";
        String body = "body";
        String nickname = "nickname";
        Long postId = 1L;


        PostEntity postEntity = PostFixture.get(nickname, postId, 1L);
        AccountEntity accountEntity = postEntity.getAccountEntity();
        /// mocking

        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());


        CustomApiException exception = assertThrows(CustomApiException.class, () -> postService.modify(title, body, nickname, postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("포스트 수정 권한이 없어 에러가 발생합니다.")
    @Test
    void post_no_atho_exception() {
        String title = "title";
        String body = "body";
        String nickname = "nickname";
        Long postId = 1L;


        PostEntity postEntity = PostFixture.get(nickname, postId, 1L);
        AccountEntity accountEntity1 = AccountFixture.get("nickname1", "password1",2L);
        /// mocking

        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity1));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        CustomApiException exception = assertThrows(CustomApiException.class, () -> postService.modify(title, body, nickname, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }

    @DisplayName("포스트 삭제 하였습니다.")
    @Test
    void post_delete() {
        String nickname = "nickname";
        Long postId = 1L;


        PostEntity postEntity = PostFixture.get(nickname, postId, 1L);
        AccountEntity accountEntity = postEntity.getAccountEntity();
        /// mocking

        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));


        Assertions.assertDoesNotThrow(() -> postService.delete(nickname, 1L));
    }

    @DisplayName("포스트 삭제시 존재 하지 않아서 에러가 발생하였습니다.")
    @Test
    void post_not_find_delete_exception() {
        String nickname = "nickname";
        Long postId = 1L;


        PostEntity postEntity = PostFixture.get(nickname, postId, 1L);
        AccountEntity accountEntity = postEntity.getAccountEntity();
        /// mocking

        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());


        CustomApiException exception = assertThrows(CustomApiException.class, () -> postService.delete(nickname, 1L));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("포스트 삭제 권한이 없어 에러가 발생합니다.")
    @Test
    void post_no_atho_delete_exception() {

        String nickname = "nickname";
        Long postId = 1L;


        PostEntity postEntity = PostFixture.get(nickname, postId, 1L);
        AccountEntity accountEntity1 = AccountFixture.get("nickname1", "password1",2L);
        /// mocking

        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity1));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        CustomApiException exception = assertThrows(CustomApiException.class, () -> postService.delete(nickname, 1L));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }
}



































