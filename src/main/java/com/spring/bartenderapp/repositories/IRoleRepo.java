package com.spring.bartenderapp.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bartenderapp.models.Role;
import com.spring.bartenderapp.models.User;

@Repository
public interface IRoleRepo extends JpaRepository<Role,Integer> {
  
}