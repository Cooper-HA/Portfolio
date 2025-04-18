package com.cooper.project.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooper.project.*;
import com.cooper.project.domain.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{
}
