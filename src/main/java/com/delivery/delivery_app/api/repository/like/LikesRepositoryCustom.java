package com.delivery.delivery_app.api.repository.like;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.like.LikesEntity;
import com.delivery.delivery_app.domain.post.PostEntity;

import java.util.List;
import java.util.Optional;

public interface LikesRepositoryCustom {

    Optional<LikesEntity> findByAccountAndPost(AccountEntity accountEntity, PostEntity postEntity);

    Long findByPost(PostEntity findPostEntity);
}
