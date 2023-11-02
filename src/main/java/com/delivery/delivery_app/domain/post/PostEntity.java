package com.delivery.delivery_app.domain.post;

import com.delivery.delivery_app.domain.common.BaseEntity;
import com.delivery.delivery_app.domain.account.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;



@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "post")
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() where post_id=?")
@Where(clause = "deleted_at is NULL")
public class PostEntity extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    private PostEntity(String title, String body, AccountEntity accountEntity) {
        this.title = title;
        this.body = body;
        this.accountEntity = accountEntity;
    }

    public static PostEntity of(String title, String body, AccountEntity accountEntity) {
        return PostEntity.builder()
                .title(title)
                .body(body)
                .accountEntity(accountEntity)
                .build();
    }

    /**
     * modify
     */
    public void modifyTitle(String title) {
        this.title = title;
    }

    public void modifyBody(String body) {
        this.body = body;
    }

    /**
     * 사용자 정의 생성자
     */
    public PostEntity(Long id, AccountEntity accountEntity) {
        this.id = id;
        this.accountEntity = accountEntity;
    }
}
