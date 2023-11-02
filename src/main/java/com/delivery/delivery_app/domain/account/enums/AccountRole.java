package com.delivery.delivery_app.domain.account.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountRole {

    ADMIN("판매자"),
    USER("회원");

    private final String text;


}
