package com.spring.bartenderapp;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bartenderapp.models.Role;
import com.spring.bartenderapp.models.User;
import com.spring.bartenderapp.repositories.IUserRepo;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
	@Autowired
	private IUserRepo _userRepo;

	public ApplicationUserDetailsService(IUserRepo userRepo) {
		_userRepo = userRepo;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = _userRepo.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(username);

		Set<Role> roles = user.getRoles();

		return new UserPrincipal(user, roles);

	}
}
