package com.cooper.project;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooper.project.db.OrderRecipeRepository;
import com.cooper.project.db.OrderRepository;
import com.cooper.project.db.MessageRepository;

import com.cooper.project.db.RecipeRepository;
import com.cooper.project.db.UserRepository;
import com.cooper.project.domain.Message;
import com.cooper.project.domain.User;

@Controller
@RequestMapping("/")
public class MainController {

    private final RecipeRepository recipeRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MainController(OrderRepository orderRepository, RecipeRepository recipeRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.recipeRepository = recipeRepository;
        this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.messageRepository = messageRepository;
    }
    @RequestMapping(value = "/", method = POST)
    public String setMessage(@ModelAttribute("messageForm") MessageForm messageForm, Errors errors, Model model) {
    	System.err.println(messageForm);
    	
    	Message message = new Message();
    	message.setCustomerName(messageForm.getCustomerName());
    	message.setCustomerEmail(messageForm.getCustomerEmail());
    	message.setMessageContent(messageForm.getMessage());
    	messageRepository.save(message);
    	model.addAttribute("messageForm", new MessageForm());
        return "main/main";
    }
    @RequestMapping(value = "/", method = GET)
    public String getMainPage(Model model) {
        model.addAttribute("messageForm", new MessageForm());
        return "main/main";
    }
    @RequestMapping(value = "/login", method = GET)
    public String getLoginPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "main/login";
    }
    @RequestMapping(value = "/api/users", method = GET)
    public @ResponseBody List<User> getLoginAPI(Model model) {
        List <User> users = userRepository.findAll();
        return users;
    }

}
