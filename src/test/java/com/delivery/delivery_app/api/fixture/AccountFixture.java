package com.delivery.delivery_app.api.fixture;

import com.delivery.delivery_app.domain.account.AccountEntity;

public class AccountFixture {

    public static AccountEntity get(String nickname, String password, Long accountId) {
        AccountEntity accountEntity = new AccountEntity(accountId, nickname,password);
        return accountEntity;
    }
}
