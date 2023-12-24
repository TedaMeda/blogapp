package com.tedameda.blogapp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
