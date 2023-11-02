package com.delivery.delivery_app.domain.like;

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
@Table(name = "likes")
@SQLDelete(sql = "UPDATE likes_id SET deleted_at = NOW() where likes_id=?")
@Where(clause = "deleted_at is NULL")
public class LikesEntity extends BaseEntity {

    @Id
    @Column(name = "likes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    private LikesEntity(AccountEntity accountEntity, PostEntity postEntity) {
        this.accountEntity = accountEntity;
        this.postEntity = postEntity;
    }

    public static LikesEntity of(AccountEntity accountEntity, PostEntity postEntity) {
        return LikesEntity.builder()
                .accountEntity(accountEntity)
                .postEntity(postEntity)
                .build();
    }
}
