package com.delivery.delivery_app.api.service.post;


import com.delivery.delivery_app.api.exception.common.CustomApiException;
import com.delivery.delivery_app.api.exception.common.ErrorCode;
import com.delivery.delivery_app.api.repository.account.AccountRepository;
import com.delivery.delivery_app.api.repository.comment.PostCommentRepository;
import com.delivery.delivery_app.api.repository.like.LikesRepository;
import com.delivery.delivery_app.api.repository.like.LikesRepositoryCustom;
import com.delivery.delivery_app.api.repository.post.PostJpaRepository;
import com.delivery.delivery_app.api.repository.post.PostRepository;
import com.delivery.delivery_app.api.service.post.model.Post;
import com.delivery.delivery_app.api.service.post.model.PostComment;
import com.delivery.delivery_app.domain.account.AccountEntity;

import com.delivery.delivery_app.domain.comment.PostCommentEntity;
import com.delivery.delivery_app.domain.like.LikesEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final AccountRepository accountRepository;
    private final PostJpaRepository postJpaRepository;

    // like repository
    private final LikesRepositoryCustom likesRepositoryCustom;
    private final LikesRepository likesRepository;

    // postComment & custom
    private final PostCommentRepository postCommentRepository;




    // Post 작성 및 수정시 nickname(유저 식별자)를 박아서 존재 여부 체크
    @Transactional
    public void create(String title, String body, String nickname) {
        // 유저가 존재 하는가?
        AccountEntity findAccountEntity = findAccountEntityOrException(nickname);
        // Post 저장
        postRepository.save(PostEntity.of(title, body, findAccountEntity));
    }

    @Transactional
    public Post modify(String title, String body, String nickname, Long postId) {
        // 유저가 존재 하는가?
        AccountEntity findAccountEntity = findAccountEntityOrException(nickname);
        // Post 가 존재하는 가?
        PostEntity findPostEntity = findPostEntityOrException(postId);
        // Post 작성자가 맞는가?
        if(findPostEntity.getAccountEntity() != findAccountEntity) {
            throw new CustomApiException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", nickname, postId));
        }

        findPostEntity.modifyTitle(title);
        findPostEntity.modifyBody(body);
        // Post 수정
        return Post.of(postRepository.save(findPostEntity));
    }

    @Transactional
    public void delete(String nickname, Long postId) {
        // 유저가 존재 하는가?
        AccountEntity findAccountEntity = findAccountEntityOrException(nickname);
        // Post 가 존재하는 가?
        PostEntity findPostEntity = findPostEntityOrException(postId);
        // Post 작성자가 맞는가?
        if(findPostEntity.getAccountEntity() != findAccountEntity) {
            throw new CustomApiException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", nickname, postId));
        }

        postRepository.delete(findPostEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postJpaRepository.findAll_Querydsl(pageable).map(Post::of);
    }

    public Page<Post> my(String nickname, Pageable pageable) {
        // 유저가 존재 하는가?
        AccountEntity findAccountEntity = findAccountEntityOrException(nickname);
        return postJpaRepository.findByAccountEntity_Querydsl(findAccountEntity, pageable).map(Post::of);
    }

    @Transactional
    public void like(Long postId, String nickname) {
        // 유저가 존재 하는가?
        AccountEntity findAccountEntity = findAccountEntityOrException(nickname);
        // Post 가 존재하는 가?
        PostEntity findPostEntity = findPostEntityOrException(postId);

        //like를 한 계정인지 체크
        try {
            likesRepositoryCustom.findByAccountAndPost(findAccountEntity, findPostEntity).ifPresent(
                    it -> {
                        throw new CustomApiException(ErrorCode.ALREADY_LIKED, String.format("nickname %s aleady like post %d", nickname, postId));
                    }
            );
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // like 하여 저장하기
        likesRepository.save(LikesEntity.of(findAccountEntity, findPostEntity));
    }

    public Long likeCount(Long postId) {
        // Post 가 존재하는 가?
        PostEntity findPostEntity = findPostEntityOrException(postId);
        return likesRepositoryCustom.findByPost(findPostEntity);
    }

    @Transactional
    public void comment(Long postId, String nickname, String comment) {
        // 유저가 존재 하는가?
        AccountEntity findAccountEntity = findAccountEntityOrException(nickname);
        // Post 가 존재하는 가?
        PostEntity findPostEntity = findPostEntityOrException(postId);

        postCommentRepository.save(PostCommentEntity.of(findAccountEntity, findPostEntity, comment));
    }

    public Page<PostComment> getComment(Long postId, Pageable pageable) {
        // Post 가 존재하는 가?
        PostEntity findPostEntity = findPostEntityOrException(postId);

        return postCommentRepository.findByPost(findPostEntity, pageable).map(PostComment::of);
    }

    /**
     * 편의 메소드
     */

    private PostEntity findPostEntityOrException(Long postId) {
        // Post 가 존재하는 가?
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
    }

    private AccountEntity findAccountEntityOrException(String nickname) {
        // Post 가 존재하는 가?
        return accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomApiException(ErrorCode.DUPLICATED_ACCOUNT_NICKNAME, String.format("%s nof founded", nickname)));
    }


}



































