package com.delivery.delivery_app.api.repository.redis;

import com.delivery.delivery_app.api.controller.account.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class AccountRedisRepository {

    private final RedisTemplate<String, Account> accountRedisTemplate;
    private final static Duration ACCOUNT_CACHE_TTL = Duration.ofDays(3);

    public void setAccount(Account account) {
        String key = getKey(account.getNickname());
        log.info("Set Account to Redis {}, {}", key, account);
        accountRedisTemplate.opsForValue().set(key, account, ACCOUNT_CACHE_TTL);
    }

    public Optional<Account> getAccount(String nickname) {
        String key = getKey(nickname);
        Account account = accountRedisTemplate.opsForValue().get(key);
        log.info("Set Account to Redis {}, {}", key, account);
        return Optional.ofNullable(account);
    }

    private String getKey(String nickname) {

        return "ACCOUNT:" + nickname;
    }

}
