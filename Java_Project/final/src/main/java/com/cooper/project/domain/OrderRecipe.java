package com.cooper.project.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER_RECIPES")
@Data
@NoArgsConstructor
public class OrderRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_RECIPE_ID", unique = true, nullable = false)
    private Long orderRecipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID", nullable = false)
    private Recipe recipe;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
}
