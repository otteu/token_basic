package com.delivery.delivery_app.api.repository.like;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.like.LikesEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Long> {


}
