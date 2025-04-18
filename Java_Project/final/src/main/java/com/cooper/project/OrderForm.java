package com.cooper.project;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OrderForm {

    private String customerName;
    private List<Long> recipeList = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
}
