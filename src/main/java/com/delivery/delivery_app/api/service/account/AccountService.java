package com.delivery.delivery_app.api.service.account;

import com.delivery.delivery_app.api.controller.account.model.Account;
import com.delivery.delivery_app.api.exception.common.CustomApiException;
import com.delivery.delivery_app.api.exception.common.ErrorCode;
import com.delivery.delivery_app.api.repository.account.AccountRepository;
import com.delivery.delivery_app.api.repository.redis.AccountRedisRepository;
import com.delivery.delivery_app.api.util.JwtTokenUtils;
import com.delivery.delivery_app.domain.account.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {


    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder encoder;

    // redis
//    private final AccountRedisRepository accountRedisRepository;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public Account loadAccountBynickname(String nickname) {

       return accountRepository.findByNickname(nickname).map(Account::of).orElseThrow(
               () -> new CustomApiException(ErrorCode.ACCOUNT_NOT_FOUND, String.format("%s not found",nickname)));


//        return accountRedisRepository.getAccount(nickname).orElseGet(
//                () -> accountRepository.findByNickname(nickname).map(Account::of).orElseThrow(
//                        () -> new CustomApiException(ErrorCode.ACCOUNT_NOT_FOUND, String.format("%s not found",nickname))
//                )
//        );

    }

    @Transactional
    public AccountEntity join(String nickname, String password) {
        // 회원가입 된 유저 있는 지 확인
        accountRepository.findByNickname(nickname)
                .ifPresent(it -> {
                    throw new CustomApiException(ErrorCode.DUPLICATED_ACCOUNT_NICKNAME, String.format("%s is duplicated", nickname));
                });

        // 회원가입 진행 = 등록
        accountRepository.save(new AccountEntity(nickname, encoder.encode(password)));
        AccountEntity findAccountEntity = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException());

        return findAccountEntity;
    }

    // Todo : implement : token 값을 반환 한다.
    public String login(String nickname, String password) {fatkun
        // 회원가입 여부 확인
        AccountEntity findAccountEntity = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomApiException(ErrorCode.DUPLICATED_ACCOUNT_NICKNAME, ""));
//        Account findRedisAccount = loadAccountBynickname(nickname);
//        accountRedisRepository.setAccount(findRedisAccount);

        // 비밀번호 확인
        if(!encoder.matches(password, findAccountEntity.getPassword())) {
            throw new CustomApiException(ErrorCode.ACCOUNT_NOT_FOUND, String.format("%s not founded", nickname));
        }

        // 토큰 생성
        return JwtTokenUtils.generateToken(nickname, secretKey, expiredTimeMs);
    }

}
