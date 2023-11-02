package com.delivery.delivery_app.api.controller.account.model;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.account.enums.AccountRole;
import com.delivery.delivery_app.domain.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Account extends BaseEntity implements UserDetails {
    private Long id;

    private String nickname;

//    private String password;

    private AccountRole role = AccountRole.USER;

    private LocalDateTime deletedAt;

    @Builder
    private Account(Long id, String nickname, AccountRole role, LocalDateTime deletedAt) {
        this.id = id;
        this.nickname = nickname;
//        this.password = password;
        this.role = role;
        this.deletedAt = deletedAt;
    }

    public static Account of(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .nickname(accountEntity.getNickname())
//                .password(accountEntity.getPassword())
                .role(accountEntity.getRole())
                .deletedAt(accountEntity.getDeletedAt())
                .build();
    }

    /**
     * Security 인증 설정
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.deletedAt == null;
    }
}
