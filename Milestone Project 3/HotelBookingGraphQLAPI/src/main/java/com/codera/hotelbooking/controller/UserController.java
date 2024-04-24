package com.codera.hotelbooking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.codera.hotelbooking.model.authentication.User;
import com.codera.hotelbooking.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;


@Controller
@Tag(name="UserController", description="User Management")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    
 
   
    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

 
    @QueryMapping
    public Optional<User> getUserById(@Argument Long id) {
        return userService.getUserById(id);
        
    }

    
  
   @MutationMapping
    public User createUser(@Argument String username, @Argument String password, @Argument String email, @Argument String role ) {
    	User user=new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setEmail(email);
    	user.setRole(role);
    	
        return userService.createUser(user);
		
    }

  
    @MutationMapping 
    public User updateUser(
    	    @Argument Long userId,
    	    @Argument String username,
    	    @Argument String password,
    	    @Argument String email,
    	    @Argument String role
    	) {
    	    // Retrieve the user entity based on the provided userId
    	    Optional<User> optionalUser = userService.getUserById(userId);
    	    if (optionalUser.isEmpty()) {
    	        throw new IllegalArgumentException("User with id " + userId + " not found");
    	    }

    	    // Extract the User object from the Optional
    	    User user = optionalUser.get();

    	    // Update the user attributes
    	    user.setUsername(username);
    	    user.setPassword(password);
    	    user.setEmail(email);
    	    user.setRole(role);

    	    // Call the user service to update the user
    	    return userService.updateUser(user);
    	}

  
    @MutationMapping    
    public String deleteUser(@Argument Long userId) {
        // Check if the user exists
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }

        // Delete the user
        userService.deleteUser(userId);

        return "User with id " + userId + " has been successfully deleted";
    }
}
