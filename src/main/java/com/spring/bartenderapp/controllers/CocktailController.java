package com.spring.bartenderapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bartenderapp.models.Cocktail;
import com.spring.bartenderapp.models.Ingredient;
import com.spring.bartenderapp.models.User;
import com.spring.bartenderapp.repositories.ICocktailRepo;
import com.spring.bartenderapp.repositories.IIngredientRepo;

@RestController
public class CocktailController {

	private ICocktailRepo cocktailRepo;
	private IIngredientRepo ingredientRepo;

	public CocktailController(ICocktailRepo cocktailRepo, IIngredientRepo ingredientRepo ) {
		this.cocktailRepo = cocktailRepo;
		this.ingredientRepo = ingredientRepo;
	}  

	@PostMapping(path = "/cocktail/add")
	public ResponseEntity<Cocktail> addCocktail(@RequestParam(value = "name") String name,
			@RequestParam(value = "instructions") String instructions,
			@RequestParam(value = "isAlcoholic") boolean isAlcoholic,
			@RequestParam(value = "ingredients", required = false) String ingredients, HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} 
 
		Set<Ingredient> ingredientsOfCocktail = parseIngredients(ingredients);

		Cocktail cocktail = new Cocktail();
		cocktail.setName(name);
		cocktail.setInstructions(instructions);
		cocktail.setAlcoholic(isAlcoholic);
		cocktail.setIngredients(ingredientsOfCocktail);
		cocktail.setImage("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name);

		return new ResponseEntity<>(cocktailRepo.saveAndFlush(cocktail), HttpStatus.OK);

	}

	private Set<Ingredient> parseIngredients(String ingredients) {
		Set<Ingredient> ingredientsOfCocktail = new HashSet<>();
		
		String[] ingrIds=ingredients.split(",");    
		
		for(int i=0; i<ingrIds.length;i++)
		{
			int id= Integer.parseInt(ingrIds[i]);
			Optional<Ingredient> ingredient = ingredientRepo.findById(id); 
			if (ingredient.isPresent())
				ingredientsOfCocktail.add(ingredient.get());
		}
		return ingredientsOfCocktail;
	}

	@GetMapping(path = "/cocktail/get_all")
	public List<Cocktail> getAllComments(HttpSession session) {
		return cocktailRepo.findAll();
	}

	@PostMapping(path = "/cocktail/edit")
	public ResponseEntity<Cocktail> editCocktail(@RequestParam(value = "id") int id,
			@RequestParam(value = "name") String name, @RequestParam(value = "instructions") String instructions,
			@RequestParam(value = "isAlcoholic") boolean isAlcoholic,
			@RequestParam(value = "ingredients", required = false)  String ingredients, HttpSession session) {
        
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		Optional<Cocktail> cocktail = cocktailRepo.findById(id);
		if (!cocktail.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		// if the user edited the name - check for existing with such name
		if (!cocktail.get().getName().equals(name)) {
			Cocktail updatedCocktail = cocktailRepo.findCocktailByName(name);
			if (updatedCocktail != null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
			} else {
				cocktail.get().setName(name);
			}
		} 
 
		Set<Ingredient> ingredientsOfCocktail = parseIngredients(ingredients);
		cocktail.get().setInstructions(instructions);
		cocktail.get().setAlcoholic(isAlcoholic);
		cocktail.get().setImage("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name);
		cocktail.get().setIngredients(ingredientsOfCocktail);

		return new ResponseEntity<>(cocktailRepo.save(cocktail.get()), HttpStatus.OK);

	}
 
	@GetMapping(path = "/cocktail/edit")
	public ResponseEntity<Cocktail> editCocktail(@RequestParam(value = "id") int id, HttpSession session) {
 
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		Optional<Cocktail> cocktail = cocktailRepo.findById(id);
		if (!cocktail.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
 
		return new ResponseEntity<>(cocktail.get(), HttpStatus.OK);
  
	}
    
	@GetMapping(path = "/cocktail/search_name")
	public ResponseEntity<List<Cocktail>> findCocktailByPartOfName(@RequestParam(value = "name") String name, HttpSession session) {
		
		List<Cocktail> cocktails = cocktailRepo.findCocktailByNameIgnoreCaseContaining(name);
		return new ResponseEntity<>(cocktails, HttpStatus.OK);
	} 
	
	//Select cocktail_id,ingredient_id  from COCKTAIL_INGREDIENT  where ingredient_id = 3 OR ingredient_id = 2
	@GetMapping(path = "/cocktail/search_by_ingredient")
	public ResponseEntity<List<Cocktail>> findCocktailByIngredient(@RequestParam(value = "ingredients") int ingredientId, HttpSession session) {
	    				
		List<Cocktail> cocktails = cocktailRepo.findByIngredients_Id(ingredientId);
		return new ResponseEntity<>(cocktails, HttpStatus.OK);
	} 
	               
	@GetMapping(path = "/cocktail/search_by_ingredients")
	public ResponseEntity<List<Cocktail>> findCocktailByIngredients(@RequestParam(value = "ingredients") List<Integer> ingredientIds, HttpSession session) {
	    				
		List<Cocktail> cocktails = cocktailRepo.findByIngredients_IdIn(ingredientIds);
		
		// convert to set to remove duplicates
		Set<Cocktail> noDuplicates = new HashSet<Cocktail>(cocktails);
		cocktails= new ArrayList<>(noDuplicates);
		
		return new ResponseEntity<>(cocktails, HttpStatus.OK);
	}
	   
	@GetMapping(path = "/cocktail/search_by_alcoholic")
	public ResponseEntity<List<Cocktail>> findCocktailByIngredient(@RequestParam(value = "isAlcoholic") boolean isAlcoholic, HttpSession session) {
	    				
		List<Cocktail> cocktails = cocktailRepo.findCocktailByIsAlcoholic(isAlcoholic);
		return new ResponseEntity<>(cocktails, HttpStatus.OK);
	} 
                 
	@GetMapping(path = "/cocktail/search_by_ingredients_and_alcoholic")
	public ResponseEntity<List<Cocktail>> findCocktailByIngredientsAndIsAlcoholic(
			@RequestParam(value = "ingredients") List<Integer> ingredientIds,
			@RequestParam(value = "isAlcoholic") boolean isAlcoholic, HttpSession session) {
	    				
		List<Cocktail> cocktails = cocktailRepo.findByIngredients_IdInAndIsAlcoholic(ingredientIds,isAlcoholic);
		
		// convert to set to remove duplicates
		Set<Cocktail> noDuplicates = new HashSet<Cocktail>(cocktails);
		cocktails= new ArrayList<>(noDuplicates);
		   
		return new ResponseEntity<>(cocktails, HttpStatus.OK);
	}
	 
	@PostMapping(path = "/cocktail/delete")
	public ResponseEntity<Boolean> deleteCocktail(@RequestParam(value = "id") int id, HttpSession session) {
 
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		Optional<Cocktail> cocktail = cocktailRepo.findById(id);
		if (!cocktail.isPresent()) {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		cocktailRepo.deleteById(cocktail.get().getId());
		return new ResponseEntity<>(true, HttpStatus.OK);
             
	}
}
