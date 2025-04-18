package com.cooper.project;

import lombok.Data;

@Data
public class EditIngredientForm {

	private Long ingredientId;
    private String ingredientName;
    private String ingredientUnit;
}