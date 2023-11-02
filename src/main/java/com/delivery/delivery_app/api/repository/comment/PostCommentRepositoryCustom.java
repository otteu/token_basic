package com.delivery.delivery_app.api.repository.comment;

import com.delivery.delivery_app.domain.comment.PostCommentEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostCommentRepositoryCustom {

    Page<PostCommentEntity> findByPost(PostEntity findPostEntity, Pageable pageable);

}
