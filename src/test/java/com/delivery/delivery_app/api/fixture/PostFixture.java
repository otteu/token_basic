package com.delivery.delivery_app.api.fixture;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.post.PostEntity;

public class PostFixture {

    public static PostEntity get(String nickname, Long postId, Long accountId) {
        AccountEntity accountEntity = new AccountEntity(accountId, nickname);

        PostEntity postEntity = new PostEntity(postId, accountEntity);
        return postEntity;
    }
}
