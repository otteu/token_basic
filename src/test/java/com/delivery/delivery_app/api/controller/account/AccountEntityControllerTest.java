package com.delivery.delivery_app.api.controller.account;

import com.delivery.delivery_app.api.controller.account.request.AccountJoinRequest;
import com.delivery.delivery_app.api.controller.account.request.AccountloginRequest;
import com.delivery.delivery_app.api.exception.common.CustomApiException;
import com.delivery.delivery_app.api.exception.common.ErrorCode;
import com.delivery.delivery_app.api.service.account.AccountService;
import com.delivery.delivery_app.domain.account.AccountEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountEntityControllerTest {

    // Controller 에서 요청을 받을 때 사용
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;


    @DisplayName("회원가입을 합니다.")
    @Test
    public void account_sign_up() throws Exception {

        String nickname = "nickname";
        String password = "password";

        // mockBean 으로 등록한 서비스 사용  결과 객체
        when(accountService.join(nickname, password)).thenReturn(mock(AccountEntity.class));

        // 기대하다 ( 메소드((url) )
        mockMvc.perform(post("/api/accounts/join")
                // contentType 설정
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new AccountJoinRequest(nickname, password)))
        ).andDo(print())
        // 상태값 설정
                .andExpect(status().isOk());
    }

    @DisplayName("이미 회원가입이 된 nickname 존재 하여 에러 발생합니다.")
    @Test
    public void duplicate_accountName() throws Exception {

        String nickname = "nickname";
        String password = "password";

        when(accountService.join(nickname, password)).thenThrow(new CustomApiException(ErrorCode.DUPLICATED_ACCOUNT_NICKNAME));

        mockMvc.perform(post("/api/account/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new AccountJoinRequest(nickname, password)))
        ).andDo(print())
                .andExpect(status().is(ErrorCode.DUPLICATED_ACCOUNT_NICKNAME.getStatus().value()));
    }

    @DisplayName("로그인을 합니다.")
    @Test
    public void account_login() throws Exception {

        String nickname = "nickname";
        String password = "password";

        // mockBean 으로 등록한 서비스 사용  결과 객체
        when(accountService.login(nickname, password)).thenReturn("test_token");

        // 기대하다 ( 메소드((url) )
        mockMvc.perform(post("/api/accounts/login")
                        // contentType 설정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new AccountloginRequest(nickname, password)))
                ).andDo(print())
                // 상태값 설정
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입이 되지 않는 계정으로 로그인 하는 겨우")
    @Test
    public void not_find_nickname() throws Exception {

        String nickname = "nickname";
        String password = "password";

        // mockBean 으로 등록한 서비스 사용  결과 객체
        when(accountService.login(nickname, password)).thenThrow(new CustomApiException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 기대하다 ( 메소드((url) )
        mockMvc.perform(post("/api/accounts/login")
                        // contentType 설정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new AccountloginRequest(nickname, password)))
                ).andDo(print())
                // 상태값 설정
                .andExpect(status().isNotFound());
    }

    @DisplayName("로그인시 비밀번호가 틀린 경우")
    @Test
    public void password_faild() throws Exception{

        String nickname = "nickname";
        String password = "password";

        // mockBean 으로 등록한 서비스 사용  결과 객체
        when(accountService.login(nickname, password)).thenThrow(new CustomApiException(ErrorCode.INVALID_PASSWORD));

        // 기대하다 ( 메소드((url) )
        mockMvc.perform(post("/api/accounts/login")
                        // contentType 설정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new AccountloginRequest(nickname, password)))
                ).andDo(print())
                // 상태값 설정
                .andExpect(status().isUnauthorized());
    }


}
































