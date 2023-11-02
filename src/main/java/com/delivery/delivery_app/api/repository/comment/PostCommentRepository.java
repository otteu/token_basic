package com.delivery.delivery_app.api.repository.comment;

import com.delivery.delivery_app.domain.comment.PostCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostCommentEntity, Long>, PostCommentRepositoryCustom {

}
