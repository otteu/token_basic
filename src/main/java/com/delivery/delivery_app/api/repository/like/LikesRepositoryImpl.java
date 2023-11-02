package com.delivery.delivery_app.api.repository.like;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.like.LikesEntity;

import com.delivery.delivery_app.domain.post.PostEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.delivery.delivery_app.domain.like.QLikesEntity.likesEntity;


@Repository
public class LikesRepositoryImpl implements LikesRepositoryCustom {


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public LikesRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<LikesEntity> findByAccountAndPost(AccountEntity accountEntity, PostEntity postEntity) {
        LikesEntity likesEntity1 = queryFactory
                .selectFrom(likesEntity)
                .where(
                        likesEntity.accountEntity.eq(accountEntity),
                        likesEntity.postEntity.eq(postEntity)
                )
                .fetchOne();
        return Optional.of(likesEntity1);
    }

    @Override
    public Long findByPost(PostEntity findPostEntity) {
        return queryFactory
                .selectFrom(likesEntity)
                .where(
                        likesEntity.postEntity.eq(findPostEntity)
                ).fetchCount();

    }


}
