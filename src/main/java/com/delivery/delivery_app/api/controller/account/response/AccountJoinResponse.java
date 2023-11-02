package com.delivery.delivery_app.api.controller.account.response;


import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.account.enums.AccountRole;
import lombok.*;

@Data
@NoArgsConstructor
public class AccountJoinResponse {

    private Long id;
    private String nickName;
    private AccountRole role;

    @Builder
    private AccountJoinResponse(Long id, String nickName, AccountRole role) {
        this.id = id;
        this.nickName = nickName;
        this.role = role;
    }

    public static AccountJoinResponse of(AccountEntity accountEntity) {
        return AccountJoinResponse.builder()
                .id(accountEntity.getId())
                .nickName(accountEntity.getNickname())
                .role(accountEntity.getRole())
                .build();
    }

}
