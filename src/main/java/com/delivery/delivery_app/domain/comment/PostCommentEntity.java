package com.delivery.delivery_app.domain.comment;

import com.delivery.delivery_app.api.service.post.model.Post;
import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.common.BaseEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "comment", indexes = {
    @Index(name = "post_id_idx", columnList = "post_id")
})
@SQLDelete(sql = "UPDATE comment SET deleted_at = NOW() where comment_id=?")
@Where(clause = "deleted_at is NULL")
public class PostCommentEntity extends BaseEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @Column(name = "comment")
    private String comment;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public PostCommentEntity(AccountEntity accountEntity, PostEntity postEntity, String comment) {
        this.accountEntity = accountEntity;
        this.postEntity = postEntity;
        this.comment = comment;
    }

    public static PostCommentEntity of(AccountEntity accountEntity, PostEntity postEntity, String comment) {
        return PostCommentEntity.builder()
                .accountEntity(accountEntity)
                .postEntity(postEntity)
                .comment(comment)
                .build();
    }
}
