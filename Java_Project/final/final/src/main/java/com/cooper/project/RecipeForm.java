package com.cooper.project;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RecipeForm {

    private String recipeName;
    private String recipeInstructions;
    private List<Long> ingredientList = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
}
