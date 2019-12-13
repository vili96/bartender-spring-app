package com.spring.bartenderapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bartenderapp.models.Role;

@Repository
public interface IRoleRepo extends JpaRepository<Role,Integer> {

}