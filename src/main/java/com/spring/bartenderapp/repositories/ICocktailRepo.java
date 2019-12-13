package com.spring.bartenderapp.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bartenderapp.models.Cocktail;

@Repository
public interface ICocktailRepo extends JpaRepository<Cocktail, Integer> {

	@Transactional //Try with the other import
	default Cocktail editCocktail() {


		return null;
		
	}
}
