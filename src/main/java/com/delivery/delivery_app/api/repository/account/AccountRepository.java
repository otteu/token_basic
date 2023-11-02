package com.delivery.delivery_app.api.repository.account;

import com.delivery.delivery_app.domain.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByNickname(String nickname);

}
