package com.delivery.delivery_app.api.controller.post.response;

import com.delivery.delivery_app.api.controller.account.response.AccountResponse;
import com.delivery.delivery_app.api.service.post.model.Post;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PostResponse {

    private Long id;
    private String title;
    private String body;
    private AccountResponse accountResponse;
    private LocalDateTime deletedAt;

    @Builder
    private PostResponse(Long id, String title, String body, AccountResponse accountResponse, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.accountResponse = accountResponse;
        this.deletedAt = deletedAt;
    }

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .accountResponse(AccountResponse.of(post.getAccount()))
                .deletedAt(post.getDeletedAt())
                .build();
    }


}
