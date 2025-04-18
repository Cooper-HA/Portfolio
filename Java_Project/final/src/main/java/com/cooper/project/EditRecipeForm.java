package com.cooper.project;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cooper.project.domain.Recipe;

import lombok.Data;

@Data
public class EditRecipeForm {
    private Long recipeId;
    private String recipeName;
    private String recipeInstructions;
    private List<Long> ingredientsList = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
 }


