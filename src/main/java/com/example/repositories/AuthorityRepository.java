package com.example.repositories;

import com.example.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthority(String role);
}