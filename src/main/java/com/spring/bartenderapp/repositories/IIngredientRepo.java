package com.spring.bartenderapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.bartenderapp.models.Ingredient;

public interface IIngredientRepo extends JpaRepository<Ingredient, Integer> {


}
