package com.delivery.delivery_app.api.service.account;

import com.delivery.delivery_app.api.fixture.AccountFixture;
import com.delivery.delivery_app.api.repository.account.AccountRepository;
import com.delivery.delivery_app.domain.account.AccountEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AccountEntityServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;


    @DisplayName("정상적인 회원가입이 됩니다.")
    @Test
    void sign_up_sccuess() {

        String nickname = "nickname";
        String password = "password";

        // mocking
        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(accountRepository.save(any())).thenReturn(AccountFixture.get(nickname, password,1L));

        Assertions.assertDoesNotThrow(() -> accountService.login(nickname, password));
    }

    @DisplayName("정상적인 로그인 합니다.")
    @Test
    void login_ssucess() {

        String nickname = "nickname";
        String password = "password";

        AccountEntity accountEntity = AccountFixture.get(nickname, password,1L);

        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity));
        when(encoder.matches(password, accountEntity.getPassword())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> accountService.login(nickname, password));
    }

    @DisplayName("회원가입 nickname이 이미 존재합니다..")
    @Test
    void sign_up_duplication_nickname() {

        String nickname = "nickname";
        String password = "password";

        AccountEntity accountEntity = AccountFixture.get(nickname, password,1L);
        // mocking
        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(mock(AccountEntity.class)));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(accountRepository.save(any())).thenReturn(Optional.of(mock(AccountEntity.class)));

        Assertions.assertThrows(RuntimeException.class, () -> accountService.login(nickname, password));
    }

    @DisplayName("회원가입 nickname의 password가 다릅니다.")
    @Test
    void login_not_find_account() {

        String nickname = "nickname";
        String password = "password";

        AccountEntity accountEntity = AccountFixture.get(nickname, password,1L);

        // mocking
        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.of(accountEntity));

        Assertions.assertThrows(RuntimeException.class, () -> accountService.login(nickname, password));
    }

    @DisplayName("회원가입 nickname이 없습니다.")
    @Test
    void login_password__fail() {

        String nickname = "nickname";
        String password = "password";
        String wrongPassword = "password";


        // mocking
        when(accountRepository.findByNickname(nickname)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> accountService.login(nickname, wrongPassword));
    }

}































