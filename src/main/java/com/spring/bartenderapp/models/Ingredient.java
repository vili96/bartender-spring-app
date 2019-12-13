package com.spring.bartenderapp.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false, unique = true, length = 40)
	private String name;
	
	@Column(name = "description", nullable = true, unique = false, length = 1000)
	private String description;	
	
	@Column(name = "image", columnDefinition ="varchar(255) default 'images/image_not_available.jpg'")
	private String image;
	
	@Column(name = "alcoholic", nullable = false, columnDefinition = "boolean default false")
	private boolean isAlcoholic;
	
	@ManyToMany(mappedBy = "ingredients")
	private Set<Cocktail> containedInCocktails;

	public Ingredient() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isAlcoholic() {
		return isAlcoholic;
	}

	public void setAlcoholic(boolean isAlcoholic) {
		this.isAlcoholic = isAlcoholic;
	}

	public Set<Cocktail> getContainedInCocktails() {
		return containedInCocktails;
	}

	public void setContainedInCocktails(Set<Cocktail> containedInCocktails) {
		this.containedInCocktails = containedInCocktails;
	}
	
}
