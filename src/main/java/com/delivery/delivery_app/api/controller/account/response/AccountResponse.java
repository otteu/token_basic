package com.delivery.delivery_app.api.controller.account.response;


import com.delivery.delivery_app.api.controller.account.model.Account;
import com.delivery.delivery_app.domain.account.enums.AccountRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private String nickName;
    private AccountRole role;

    @Builder
    private AccountResponse(Long id, String nickName, AccountRole role) {
        this.id = id;
        this.nickName = nickName;
        this.role = role;
    }

    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .nickName(account.getNickname())
                .role(account.getRole())
                .build();
    }

}
