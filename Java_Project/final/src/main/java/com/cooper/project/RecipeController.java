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
@RequestMapping("/recipe")
public class RecipeController {
	
	private RecipeRepository recipeRepository;
	private IngredientRepository ingredientRepository;
	private RecipeIngredientRepository recipeIngredientRepository;
	
	
	@Autowired
	public RecipeController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository) {
		  this.recipeRepository = recipeRepository;
		  this.ingredientRepository = ingredientRepository;
		  this.recipeIngredientRepository = recipeIngredientRepository;
	}
	
	@RequestMapping(value="/list", method=GET)
	public String getRecipes(Model model) {
      List<Recipe> recipes = recipeRepository.findAll();
	  model.addAttribute("recipes", recipes);
	  System.err.println("SIZE: " + recipes.size());
	  for(Recipe r: recipes)
		  System.err.println("Name: " + r.getRecipeName());
	  return "recipe/recipelist";
	}
	
    @RequestMapping(value = "/add", method = GET)
    public String showCreateForm(Model model) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("recipeForm", new RecipeForm());
        return "recipe/add";
    }
    @RequestMapping(value = "/create", method = POST)
    public String createOrder(@ModelAttribute("recipeForm") RecipeForm recipeForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "recipe/add";
        }
        System.err.println(recipeForm);
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeForm.getRecipeName());
        recipe.setRecipeInstructions(recipeForm.getRecipeInstructions());

        
        System.err.println(recipe);

        for (int i = 0; i < recipeForm.getIngredientList().size(); i++) {
            Long ingredientId = recipeForm.getIngredientList().get(i);
            int quantity = recipeForm.getQuantities().get(i);

            Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        	
            if (recipe != null) {
            	RecipeIngredient ingredientRecipe = new RecipeIngredient();
                ingredientRecipe.setRecipe(recipe);
                ingredientRecipe.setIngredient(ingredient);
                ingredientRecipe.setQuantity(quantity);

                recipe.getRecipeIngredients().add(ingredientRecipe);
            }
        }
        recipeRepository.save(recipe);
        //recipeRepository.save(recipe);
        model.addAttribute("ingredients", ingredientRepository.findAll());
        model.addAttribute("recipe", recipe);
        return "recipe/view";
    }

    @GetMapping("/edit/{recipeId}")
    public String showEditOrderForm(@PathVariable Long recipeId, Model model) {
        // Fetch the order by orderId
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Prepare a form object for editing
        EditRecipeForm editRecipeForm = new EditRecipeForm();
        editRecipeForm.setRecipeId(recipe.getRecipeId());
        editRecipeForm.setRecipeName(recipe.getRecipeName());
        editRecipeForm.setRecipeInstructions(recipe.getRecipeInstructions());


        // Collect the recipe IDs and quantities
        List<Long> ingredientList = recipe.getRecipeIngredients().stream()
                .map(recipeIngredient -> recipeIngredient.getIngredient().getIngredientId())
                .collect(Collectors.toList());
        List<Integer> quantities = recipe.getRecipeIngredients().stream()
                .map(RecipeIngredient::getQuantity)
                .collect(Collectors.toList());
        
        editRecipeForm.setIngredientsList(ingredientList);
        editRecipeForm.setQuantities(quantities);

        // Fetch all available recipes from the database
        List<Ingredient> ingredients = ingredientRepository.findAll();

        // Add the data to the model for Thymeleaf to use
        model.addAttribute("editRecipeForm", editRecipeForm);
        model.addAttribute("ingredients", ingredients);

        return "recipe/edit";  // Returns the Thymeleaf template for editing the order
    }
    
    // Handle the form submission to update the order
	    @PostMapping("/edit")
	    @Transactional
	    public String submitOrderEdit(@ModelAttribute EditRecipeForm recipeForm, Model model) {

	            System.err.println(recipeForm);
	
	            // Fetch the order to update
	            Recipe recipe = recipeRepository.findById(recipeForm.getRecipeId())
	                    .orElseThrow(() -> new RuntimeException("Order not found"));
	
	            // Update the order's customer name
	            recipe.setRecipeName(recipeForm.getRecipeName());
	            recipe.setRecipeInstructions(recipeForm.getRecipeInstructions());
	
	            // Clear existing order recipes in the database
	            recipeIngredientRepository.deleteByRecipeRecipeId(recipeForm.getRecipeId());
	
	            // Create new order recipes based on the submitted data
	            List<Long> updatedIngredientList = recipeForm.getIngredientsList();
	            List<Integer> updatedQuantities = recipeForm.getQuantities();
	            System.err.println(updatedIngredientList);
	            System.err.println(updatedQuantities);
	
	            // Validate list sizes
	            if (updatedIngredientList.size() != updatedQuantities.size()) {
	                throw new IllegalArgumentException("Recipe list and quantity list sizes do not match");
	            }
	
	            // Add new OrderRecipe objects
	            for (int i = 0; i < updatedIngredientList.size(); i++) {
	                Ingredient ingredient = ingredientRepository.findById(updatedIngredientList.get(i))
	                        .orElseThrow(() -> new RuntimeException("Recipe not found"));
	
	                RecipeIngredient recipeIngredient = new RecipeIngredient();
	                recipeIngredient.setRecipe(recipe);
	                recipeIngredient.setIngredient(ingredient);
	                recipeIngredient.setQuantity(updatedQuantities.get(i));
	
	                // Add the new orderRecipe to the order
	                recipe.getRecipeIngredients().add(recipeIngredient);
	            }
	
	            // Save the updated order
	            recipeRepository.save(recipe);
	
	            // Add attributes for view rendering
	            List<Ingredient> ingredients = ingredientRepository.findAll();
	            model.addAttribute("recipe", recipe);
	            model.addAttribute("ingredients", ingredients);
	
	            return "recipe/view"; // Return the view page for the updated order
	
	        
    }
    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long recipeId) {
            recipeRepository.deleteById(recipeId); // Your service to handle deletion
            return "recipe/recipelist"; 
    }
    @GetMapping("/view/{recipeId}")
    public String viewOrder(@PathVariable Long recipeId, Model model) {
    	Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredients);
    	return "recipe/view";
    }
}
