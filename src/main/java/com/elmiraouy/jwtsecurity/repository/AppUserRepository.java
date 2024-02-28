package com.elmiraouy.jwtsecurity.repository;

import com.elmiraouy.jwtsecurity.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findAppUserById(Long id);

}
