package com.delivery.delivery_app.api.repository.comment;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.comment.PostCommentEntity;
import com.delivery.delivery_app.domain.comment.QPostCommentEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.delivery.delivery_app.domain.comment.QPostCommentEntity.*;
import static com.delivery.delivery_app.domain.post.QPostEntity.postEntity;

@Repository
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostCommentRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PostCommentEntity> findByPost(PostEntity findPostEntity, Pageable pageable) {
        List<PostCommentEntity> content = queryFactory
                .selectFrom(postCommentEntity)
                .where(postCommentEntity.postEntity.eq(findPostEntity))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PostCommentEntity> countQuery = queryFactory
                .selectFrom(postCommentEntity);


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }



}
