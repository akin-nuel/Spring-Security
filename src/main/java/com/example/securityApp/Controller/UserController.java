package com.example.securityApp.Controller;

import com.example.securityApp.Dto.UserRegistrationRequest;
import com.example.securityApp.Dto.UserUpdateRequest;
import com.example.securityApp.Service.UserService;
import com.example.securityApp.model.AuthenticationRequest;
import com.example.securityApp.model.AuthenticationResponse;
import com.example.securityApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User saveUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        return userService.saveUser(userRegistrationRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse userLogin (@RequestBody AuthenticationRequest authenticationRequest){
        return userService.userLogin(authenticationRequest);
    }

    @GetMapping("/user")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody UserUpdateRequest userUpdateRequest){
        return userService.updateUser(id, userUpdateRequest);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }
}
