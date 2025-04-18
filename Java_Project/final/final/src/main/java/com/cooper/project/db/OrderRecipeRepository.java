package com.cooper.project.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooper.project.domain.OrderRecipe;

@Repository
public interface OrderRecipeRepository extends JpaRepository<OrderRecipe, Long> {
    // Custom method to delete all OrderRecipe records by order ID
    void deleteByOrderOrderId(Long orderId);
}
