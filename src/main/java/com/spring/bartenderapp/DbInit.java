//package com.spring.bartenderapp;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Service;
//
//import com.spring.models.Role;
//import com.spring.models.User;
//import com.spring.repositories.IUserRepo;
//
//@Service
//public class DbInit implements CommandLineRunner {
//
//	private IUserRepo userRepo;
//
//	public DbInit(IUserRepo userRepo) {
//		this.userRepo = userRepo;
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		User admin = new User("admin", "admin");
//		Role adminRole = new Role();
//		adminRole.setType("ADMIN_ROLE");
//		adminRole.setId(1000);
//		Set<Role> roles = new HashSet<>(Arrays.asList(adminRole));
//		admin.setRoles(roles);
//
//		this.userRepo.saveAndFlush(admin);
//	}
//
//}
