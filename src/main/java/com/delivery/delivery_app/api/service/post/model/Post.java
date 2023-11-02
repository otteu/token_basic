package com.delivery.delivery_app.api.service.post.model;

import com.delivery.delivery_app.api.controller.account.model.Account;
import com.delivery.delivery_app.domain.common.BaseEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class Post extends BaseEntity {

    private Long id;
    private String title;
    private String body;
    private Account account;
    private LocalDateTime deletedAt;

    @Builder
    private Post(Long id, String title, String body, Account account, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.account = account;
        this.deletedAt = deletedAt;
    }

    public static Post of(PostEntity postEntity) {
        return Post.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .account(Account.of(postEntity.getAccountEntity()))
                .deletedAt(postEntity.getDeletedAt())
                .build();
    }

}
