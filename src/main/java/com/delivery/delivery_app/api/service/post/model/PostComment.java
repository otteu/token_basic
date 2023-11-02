package com.delivery.delivery_app.api.service.post.model;

import com.delivery.delivery_app.domain.comment.PostCommentEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PostComment {

    private Long id;
    private String comment;
    private String nickname;
    private Long postId;
    private LocalDateTime deletedAt;

    @Builder
    private PostComment(Long id, String comment, String nickname, Long postId, LocalDateTime deletedAt) {
        this.id = id;
        this.comment = comment;
        this.nickname = nickname;
        this.postId= postId;
        this.deletedAt = deletedAt;
    }

    public static PostComment of(PostCommentEntity postCommentEntity) {
        return PostComment.builder()
                .id(postCommentEntity.getId())
                .comment(postCommentEntity.getComment())
                .comment(postCommentEntity.getAccountEntity().getNickname())
                .deletedAt(postCommentEntity.getDeletedAt())
                .build();
    }

}
