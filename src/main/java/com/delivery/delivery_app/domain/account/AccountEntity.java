package com.delivery.delivery_app.domain.account;


import com.delivery.delivery_app.domain.common.BaseTimeEntity;
import com.delivery.delivery_app.domain.account.enums.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "account")
@SQLDelete(sql = "UPDATE account SET deleted_at = NOW() where account_id=?")
@Where(clause = "deleted_at is NULL")
//@EqualsAndHashCode(of = "id")   // 순환참조하는 과정에서 무한 루브 방지 스택 오버 플로우
public class AccountEntity extends BaseTimeEntity implements UserDetails {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
    private String nickname;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRole role = AccountRole.USER;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

//    //    @Column(unique = true)
//    private String email;
//
//    private String userName;

//    // email 인증이 된 계정인지 여부
//    private boolean emailVerified;
//
//    // email을 인증 할 토큰 값
//    private String emailCheckToken;
//
//    // 인증이 된 시접 가입이 된것으로 할고 그 시간 기록
//    private LocalDateTime joinedAt;
//
//    /**
//     * 프로필 관련 정보들
//     */
//    private String bio;
//
//    private String url;
//
//    // 직업
//    private String occupation;
//
//    // 지역
//    private String location;

    /**
     * 사용자 정의 생성자
     */
    public AccountEntity(Long id, String nickname) {
        this.nickname = nickname;
    }

    public AccountEntity(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
    public AccountEntity(Long id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
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
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return this.deletedAt == null;
    }
}
