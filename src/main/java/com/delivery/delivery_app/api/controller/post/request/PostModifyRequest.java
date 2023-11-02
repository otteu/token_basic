package com.delivery.delivery_app.api.controller.post.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostModifyRequest {

    private String title;
    private String body;

}
