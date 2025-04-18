
package com.cooper.project.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.ToString;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RECIPES")
@Data
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPE_ID", unique = true, nullable = false)
    private Long recipeId;

    @Column(name = "RECIPE_NAME", unique = true, nullable = false, length = 40)
    private String recipeName;
    
    @ToString.Exclude 

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
	
    @Column(name = "RECIPE_INSTRUCTIONS", length = 1000)
    private String recipeInstructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderRecipe> orderRecipe;
}

