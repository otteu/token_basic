package com.delivery.delivery_app.api.controller.account.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountJoinRequest {

    private String nickname;
    private String password;
}
