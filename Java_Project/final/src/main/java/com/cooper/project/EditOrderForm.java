package com.cooper.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cooper.project.domain.Recipe;

import lombok.Data;

@Data
public class EditOrderForm {
    private Long orderId;
    private String customerName;
    private List<Long> recipeList = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
 }
