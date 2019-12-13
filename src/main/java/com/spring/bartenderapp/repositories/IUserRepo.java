package com.spring.bartenderapp.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.bartenderapp.models.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {
	User findUserByUsernameAndPassword(String username, String password);

	List<User> findByUsernameContaining(String username);

	User findByUsername(String username);
	
}
