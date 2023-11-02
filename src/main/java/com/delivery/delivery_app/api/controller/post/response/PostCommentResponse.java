package com.delivery.delivery_app.api.controller.post.response;

import com.delivery.delivery_app.api.service.post.model.PostComment;
import com.delivery.delivery_app.domain.comment.PostCommentEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PostCommentResponse {

    private Long id;
    private String comment;
    private String nickname;
    private Long postId;
    private LocalDateTime deletedAt;

    @Builder
    private PostCommentResponse(Long id, String comment, String nickname, Long postId, LocalDateTime deletedAt) {
        this.id = id;
        this.comment = comment;
        this.nickname = nickname;
        this.postId= postId;
        this.deletedAt = deletedAt;
    }

    public static PostCommentResponse of(PostComment postComment) {
        return PostCommentResponse.builder()
                .id(postComment.getId())
                .comment(postComment.getComment())
                .comment(postComment.getNickname())
                .deletedAt(postComment.getDeletedAt())
                .build();
    }

}
