package com.spring.bartenderapp.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.bartenderapp.models.Cocktail;

@Repository
public interface ICocktailRepo extends JpaRepository<Cocktail, Integer> {

	Cocktail findCocktailByName(String name);

	List<Cocktail> findCocktailByNameIgnoreCaseContaining(String name);
	  
	List<Cocktail> findCocktailByIsAlcoholic(boolean isAlcoholic);
	
	List<Cocktail> findByIngredients_Id(int id);
	
	List<Cocktail> findByIngredients_IdIn(List<Integer> ids);
	
	List<Cocktail> findByIngredients_IdAndIsAlcoholic(int ingredientId,boolean isAlcoholic);
	      
	List<Cocktail> findByIngredients_IdInAndIsAlcoholic(List<Integer> ingredientIds,boolean isAlcoholic);
        
}
