package com.cooper.project.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooper.project.domain.RecipeIngredient;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    // Custom method to delete all OrderRecipe records by order ID
    void deleteByRecipeRecipeId(Long recipeId);
}


