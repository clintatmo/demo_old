package com.example.repositories;

import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by clint on 8/31/16.
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
