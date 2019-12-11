package com.spring.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cocktails")
public class Cocktail {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false, unique = true, length = 40)
	private String name;
	
	@Column(name = "instructions", nullable = false, unique = false, length = 1000)
	private String instructions;	
	
	@Column(name = "image", columnDefinition ="varchar(255) default 'images/image_not_available.jpg'")
	private String image;
	
	@Column(name = "alcoholic", nullable = false, columnDefinition = "boolean default false")
	private boolean isAlcoholic;
	

	@ManyToMany()
	@JoinTable(name = "cocktail_ingredient", joinColumns = @JoinColumn(name = "cocktail_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private Set<Ingredient> ingredients;
}
