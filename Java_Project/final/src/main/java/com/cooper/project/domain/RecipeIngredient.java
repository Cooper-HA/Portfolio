package com.cooper.project.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "RECIPE_INGREDIENTS")
@Data
@NoArgsConstructor
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPE_INGREDIENT_ID", unique = true, nullable = false)
    private Long orderRecipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INGREDIENT_ID", nullable = false)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID", nullable = false)
    private Recipe recipe;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
}

