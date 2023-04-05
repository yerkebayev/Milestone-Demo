package com.example.thenext.controllers;

import com.example.thenext.job.CSVHelper;
import com.example.thenext.models.User;
import com.example.thenext.service.Service;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Service userService;

    public UserController(Service userService) {
        this.userService = userService;
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        Optional<User> optionalUser = userService.getUser(id);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user){
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isPresent()) {
            User us = optionalUser.get();
            us.setAge(user.getAge());
            us.setGender(user.getGender());
            us.setOccupation(user.getOccupation());
            us.setZipCode(user.getZipCode());
            userService.saveUser(us);
            return new ResponseEntity<>(us, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        if (userService.getUser(id).isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/upload")
    public String uploadFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("src/main/resources/users.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );
        String message;

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                userService.importUserCsv(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return message;
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return message;
            }
        }
        message = "Please upload a csv file!";
        return message;
    }
}
