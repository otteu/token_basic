package com.delivery.delivery_app.api.repository.post;

import com.delivery.delivery_app.domain.account.AccountEntity;
import com.delivery.delivery_app.domain.post.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByAccountEntity(AccountEntity accountEntity, Pageable pageable);

}
