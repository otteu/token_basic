package com.delivery.delivery_app.api.controller.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class LikesCountResponse {

    private Long count;

    @Builder
    private LikesCountResponse(Long count) {
        this.count = count;
    }

    public static LikesCountResponse of(Long count) {
        return LikesCountResponse.builder()
                .count(count)
                .build();
    }

}
