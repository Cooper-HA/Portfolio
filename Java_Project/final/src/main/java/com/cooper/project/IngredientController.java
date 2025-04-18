package com.cooper.project;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooper.project.db.IngredientRepository;
import com.cooper.project.db.OrderRepository;
import com.cooper.project.db.RecipeRepository;
import com.cooper.project.db.RecipeIngredientRepository;

//import com.acme.demo.db.RegionRepository;

import org.springframework.validation.Errors;

//import com.acme.demo.db.CountryRepository;
//import com.acme.demo.db.RegionRepository;
//import com.acme.demo.domain.Country;
import com.cooper.project.domain.Recipe;
import com.cooper.project.domain.Ingredient;
import com.cooper.project.domain.Order;
import com.cooper.project.domain.OrderRecipe;
import com.cooper.project.domain.RecipeIngredient;

//import com.acme.demo.domain.Region;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
	
	private RecipeRepository recipeRepository;
	private IngredientRepository ingredientRepository;
	private RecipeIngredientRepository recipeIngredientRepository;
	
	
	@Autowired
	public IngredientController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository) {
		  this.recipeRepository = recipeRepository;
		  this.ingredientRepository = ingredientRepository;
		  this.recipeIngredientRepository = recipeIngredientRepository;
	}
	
	@RequestMapping(value="/list", method=GET)
	public String getIngredients(Model model) {
      List<Ingredient> Ingredients = ingredientRepository.findAll();
	  model.addAttribute("ingredients", Ingredients);
	  System.err.println("SIZE: " + Ingredients.size());
	  for(Ingredient i: Ingredients)
		  System.err.println("Name: " + i.getIngredientName());
	  return "ingredient/ingredientlist";
	}
    @RequestMapping(value = "/add", method = GET)
    public String showCreateForm(Model model) {
        model.addAttribute("ingredientForm", new IngredientForm());
        return "ingredient/add";
    }
    @RequestMapping(value = "/create", method = POST)
    public String createIngredient(@ModelAttribute("ingredientForm") IngredientForm ingredientForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "ingredient/add";
        }
        System.err.println(ingredientForm);
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(ingredientForm.getIngredientName());
        ingredient.setIngredientUnit(ingredientForm.getIngredientUnit());

        
        System.err.println(ingredient);
        ingredientRepository.save(ingredient);
        //recipeRepository.save(recipe);
        model.addAttribute("ingredient", ingredient);
        return "ingredient/view";
    }
    @GetMapping("/view/{ingredientId}")
    public String viewIngredient(@PathVariable Long ingredientId, Model model) {
    	Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        model.addAttribute("ingredient", ingredient);
    	return "ingredient/view";
    }
    
    @GetMapping("/edit/{ingredientId}")
    public String showEditOrderForm(@PathVariable Long ingredientId, Model model) {
        // Fetch the order by orderId
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Prepare a form object for editing
        EditIngredientForm editIngredientForm = new EditIngredientForm();
        editIngredientForm.setIngredientId(ingredient.getIngredientId());
        editIngredientForm.setIngredientName(ingredient.getIngredientName());
        editIngredientForm.setIngredientUnit(ingredient.getIngredientUnit());



        // Fetch all available ingredients from the database

        // Add the data to the model for Thymeleaf to use
        model.addAttribute("editIngredientForm", editIngredientForm);

        return "ingredient/edit";  // Returns the Thymeleaf template for editing the order
    }

    // Handle the form submission to update the order
    @PostMapping("/edit")
    @Transactional
    public String submitOrderEdit(@ModelAttribute EditIngredientForm ingredientForm, Model model) {
        try {
            System.err.println(ingredientForm);

            // Fetch the order to update
            Ingredient ingredient = ingredientRepository.findById(ingredientForm.getIngredientId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found"));

            // Update the order's customer name
            ingredient.setIngredientName(ingredientForm.getIngredientName());
            ingredient.setIngredientUnit(ingredientForm.getIngredientUnit());

 
            // Save the updated 
            ingredientRepository.save(ingredient);

            // Add attributes for view rendering
            model.addAttribute("ingredient", ingredient);

            return "ingredient/view"; // Return the view page for the updated order

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "An error occurred while editing the order: " + ex.getMessage());
            return "error"; // Return an error view in case of failure
        }
    }
    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long ingredientId) {
            ingredientRepository.deleteById(ingredientId); // Your service to handle deletion
            return "ingredient/ingredientlist"; 
    }
}
