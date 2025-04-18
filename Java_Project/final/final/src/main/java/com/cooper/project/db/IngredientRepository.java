package com.cooper.project.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooper.project.*;
import com.cooper.project.domain.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
}