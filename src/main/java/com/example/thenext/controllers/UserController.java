package com.example.thenext.controllers;

import com.example.thenext.models.User;
import com.example.thenext.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private Service userService;


    @GetMapping
    public List<User> getUsers() {
        return userService.allUsers();
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id).get();
    }
    @PostMapping
    public String saveUser(@RequestBody User user) {
        user.setId(1L);
        userService.saveUser(user);
        return "Saved...";
    }
    @PutMapping("/{id}")
    public String updateMovie(@PathVariable long id, @RequestBody User user){
        Optional<User> optionalUser = userService.getUser(id);
        User us = optionalUser.get();
        us.setAge(user.getAge());
        us.setGender(user.getGender());
        us.setOccupation(user.getOccupation());
        user.setZipCode(user.getZipCode());
        return "Updated...";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "Deleted...";
    }
}
