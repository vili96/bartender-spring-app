package com.spring.bartenderapp.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bartenderapp.models.Cocktail;
import com.spring.bartenderapp.models.Ingredient;
import com.spring.bartenderapp.models.User;
import com.spring.bartenderapp.repositories.ICocktailRepo;

@RestController
public class CocktailController {

	private ICocktailRepo cocktailRepo;

	public CocktailController(ICocktailRepo cocktailRepo) {
		this.cocktailRepo = cocktailRepo;
	}

	@PostMapping(path = "/cocktail/add")
	public ResponseEntity<Cocktail> addCocktail(@RequestParam(value = "name") String name,
			@RequestParam(value = "instructions") String instructions,
			@RequestParam(value = "isAlcoholic") boolean isAlcoholic,
			@RequestParam(value = "ingredients", required = false) Set<Ingredient> ingredients, HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		Cocktail cocktail = new Cocktail();
		cocktail.setName(name);
		cocktail.setInstructions(instructions);
		cocktail.setAlcoholic(isAlcoholic);
		cocktail.setImage("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name);
//		https://www.thecocktaildb.com/images/ingredients/Bourbon.png

		return new ResponseEntity<>(cocktailRepo.saveAndFlush(cocktail), HttpStatus.OK);

	}

	@GetMapping(path = "/cocktail/get_all")
	public List<Cocktail> getAllComments(HttpSession session) {
		return cocktailRepo.findAll();
	}
	
	@PostMapping(path = "/cocktail/edit")
	public ResponseEntity<Cocktail> editCocktail(@RequestParam(value = "id") String id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "instructions") String instructions,
			@RequestParam(value = "isAlcoholic") boolean isAlcoholic,
			@RequestParam(value = "ingredients", required = false) Set<Ingredient> ingredients,   HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		Cocktail cocktail = new Cocktail();
		cocktail.setName(name);
		cocktail.setInstructions(instructions);
		cocktail.setAlcoholic(isAlcoholic);
		cocktail.setImage("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name);
//		https://www.thecocktaildb.com/images/ingredients/Bourbon.png

		return new ResponseEntity<>(cocktailRepo.saveAndFlush(cocktail), HttpStatus.OK);

	}
	
	@GetMapping(path = "/cocktail/edit")
	public ResponseEntity<Cocktail> editCocktail(@RequestParam(value = "id") int id ,   HttpSession session) {

//		User user = (User) session.getAttribute("user");
//		if (user == null) {
//			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//		}
		Optional<Cocktail> cocktail = cocktailRepo.findById(id);
		if (!cocktail.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(cocktail.get(), HttpStatus.OK);

	}
}
