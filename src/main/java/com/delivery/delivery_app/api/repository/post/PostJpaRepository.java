package com.delivery.delivery_app.api.repository.post;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.post.PostEntity;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.delivery.delivery_app.domain.post.QPostEntity.postEntity;

@Repository
public class PostJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<PostEntity> findAll_Querydsl(Pageable pageable) {
        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .fetch();

        JPAQuery<PostEntity> countQuery = queryFactory
                .selectFrom(postEntity);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    public Page<PostEntity> findByAccountEntity_Querydsl(AccountEntity accountEntity, Pageable pageable) {
        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .where(
                        postEntity.accountEntity.eq(accountEntity)
                )
                .offset(pageable.getOffset())
                // 한번 조회 할때 몇개 까지 가져 올가여? 기본 20개
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PostEntity> countQuery = queryFactory
                .selectFrom(postEntity)
                .where(
                        postEntity.accountEntity.eq(accountEntity)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }


}
