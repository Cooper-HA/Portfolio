package com.cooper.project;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.function.Function;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooper.project.OrderForm;
import com.cooper.project.db.OrderRecipeRepository;
import com.cooper.project.db.OrderRepository;
import com.cooper.project.db.RecipeRepository;
//import com.acme.demo.db.RegionRepository;

import org.springframework.validation.Errors;

import com.cooper.project.domain.Ingredient;
//import com.acme.demo.db.CountryRepository;
//import com.acme.demo.db.RegionRepository;
//import com.acme.demo.domain.Country;
import com.cooper.project.domain.Order;
import com.cooper.project.domain.Recipe;
import com.cooper.project.domain.OrderRecipe;


//import com.acme.demo.domain.Region;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final RecipeRepository recipeRepository;
    private final OrderRepository orderRepository;
    private final OrderRecipeRepository orderRecipeRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, RecipeRepository recipeRepository, OrderRecipeRepository orderRecipeRepository) {
        this.recipeRepository = recipeRepository;
        this.orderRepository = orderRepository;
		this.orderRecipeRepository = orderRecipeRepository;
    }

    @RequestMapping(value = "/list", method = GET)
    public String getOrders(Model model) {
        List<Order> orderList = orderRepository.findAll();
        model.addAttribute("orders", orderList);
        return "order/orderlist";
    }

    @RequestMapping(value = "/add", method = GET)
    public String showCreateForm(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        model.addAttribute("recipes", recipes);
        model.addAttribute("orderForm", new OrderForm());
        return "order/add";
    }

    @RequestMapping(value = "/create", method = POST)
    public String createOrder(@ModelAttribute("orderForm") OrderForm orderForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("recipes", recipeRepository.findAll());
            return "order/add";
        }

        Order order = new Order();
        order.setCustomerName(orderForm.getCustomerName());

        for (int i = 0; i < orderForm.getRecipeList().size(); i++) {
            Long recipeId = orderForm.getRecipeList().get(i);
            int quantity = orderForm.getQuantities().get(i);

            Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
            if (recipe != null) {
                OrderRecipe orderRecipe = new OrderRecipe();
                orderRecipe.setOrder(order);
                orderRecipe.setRecipe(recipe);
                orderRecipe.setQuantity(quantity);

                order.getOrderRecipe().add(orderRecipe);
            }
        }

        orderRepository.save(order);
        model.addAttribute("recipes", recipeRepository.findAll());
        model.addAttribute("order", order);
        return "order/view";
    }
    @RequestMapping(value = "/customerCreate", method = POST)
    public String createCustomerOrder(@ModelAttribute("orderForm") OrderForm orderForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("recipes", recipeRepository.findAll());
            return "order/add";
        }

        Order order = new Order();
        order.setCustomerName(orderForm.getCustomerName());

        for (int i = 0; i < orderForm.getRecipeList().size(); i++) {
            Long recipeId = orderForm.getRecipeList().get(i);
            int quantity = orderForm.getQuantities().get(i);

            Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
            if (recipe != null) {
                OrderRecipe orderRecipe = new OrderRecipe();
                orderRecipe.setOrder(order);
                orderRecipe.setRecipe(recipe);
                orderRecipe.setQuantity(quantity);

                order.getOrderRecipe().add(orderRecipe);
            }
        }

        orderRepository.save(order);
        model.addAttribute("recipes", recipeRepository.findAll());
        model.addAttribute("order", order);
        return "order/CustomerView";
    }
    @GetMapping("/edit/{orderId}")
    public String showEditOrderForm(@PathVariable Long orderId, Model model) {
        // Fetch the order by orderId
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Prepare a form object for editing
        EditOrderForm editOrderForm = new EditOrderForm();
        editOrderForm.setOrderId(order.getOrderId());
        editOrderForm.setCustomerName(order.getCustomerName());

        // Collect the recipe IDs and quantities
        List<Long> recipeList = order.getOrderRecipe().stream()
                .map(orderRecipe -> orderRecipe.getRecipe().getRecipeId())
                .collect(Collectors.toList());
        List<Integer> quantities = order.getOrderRecipe().stream()
                .map(OrderRecipe::getQuantity)
                .collect(Collectors.toList());
        
        editOrderForm.setRecipeList(recipeList);
        editOrderForm.setQuantities(quantities);

        // Fetch all available recipes from the database
        List<Recipe> recipes = recipeRepository.findAll();

        // Add the data to the model for Thymeleaf to use
        model.addAttribute("orderForm", editOrderForm);
        model.addAttribute("recipes", recipes);

        return "order/edit";  // Returns the Thymeleaf template for editing the order
    }

    // Handle the form submission to update the order
    @PostMapping("/edit")
    @Transactional
    public String submitOrderEdit(@ModelAttribute EditOrderForm orderForm, Model model) {
        try {
            System.err.println(orderForm);

            // Fetch the order to update
            Order order = orderRepository.findById(orderForm.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // Update the order's customer name
            order.setCustomerName(orderForm.getCustomerName());

            // Clear existing order recipes in the database
            orderRecipeRepository.deleteByOrderOrderId(orderForm.getOrderId());

            // Create new order recipes based on the submitted data
            List<Long> updatedRecipeList = orderForm.getRecipeList();
            List<Integer> updatedQuantities = orderForm.getQuantities();

            // Validate list sizes
            if (updatedRecipeList.size() != updatedQuantities.size()) {
                throw new IllegalArgumentException("Recipe list and quantity list sizes do not match");
            }

            // Add new OrderRecipe objects
            for (int i = 0; i < updatedRecipeList.size(); i++) {
                Recipe recipe = recipeRepository.findById(updatedRecipeList.get(i))
                        .orElseThrow(() -> new RuntimeException("Recipe not found"));

                OrderRecipe orderRecipe = new OrderRecipe();
                orderRecipe.setOrder(order);
                orderRecipe.setRecipe(recipe);
                orderRecipe.setQuantity(updatedQuantities.get(i));

                // Add the new orderRecipe to the order
                order.getOrderRecipe().add(orderRecipe);
            }

            // Save the updated order
            orderRepository.save(order);

            // Add attributes for view rendering
            List<Recipe> recipes = recipeRepository.findAll();
            model.addAttribute("order", order);
            model.addAttribute("recipes", recipes);

            return "order/view"; // Return the view page for the updated order

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "An error occurred while editing the order: " + ex.getMessage());
            return "error"; // Return an error view in case of failure
        }
    }
    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
            orderRepository.deleteById(orderId); // Your service to handle deletion
            return "order/orderlist"; 
    }
    @GetMapping("/view/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
    	Order order = orderRepository.findById(orderId).orElse(null);
        List<Recipe> recipes = recipeRepository.findAll();
        model.addAttribute("order", order);
        model.addAttribute("recipes", recipes);
    	return "order/view";
    }
}


