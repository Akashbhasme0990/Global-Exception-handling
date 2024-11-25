package com.CRUD.Operations.Controllers;

import com.CRUD.Operations.Entity.User;
import com.CRUD.Operations.Repository.UserRepository;
import com.CRUD.Operations.Service.UserService;
import com.CRUD.Operations.exceptions.EmptyInputException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    // Constructor injection
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/Users")
    @Operation(summary = "Gets all the users", description = "Users must exist")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/save")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        List<User> savedUsers = userRepository.saveAll(users);
        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EmptyInputException("User not found"));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") int userId) {
        try {
            userRepository.deleteById(userId);
            return "User deleted successfully!";
        } catch (Exception e) {
            return "Error deleting user: " + e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/akash")
    public String getDetailsOfStudent() {
        return "my name is Akash Bhasme";
    }

    @GetMapping("/usernames")
    public Map<User, ArrayList<String>> getUserNames() {
        return userService.getUserData();
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterUsers(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Long phone) {

        List<User> users;

        if (userName != null) {
            users = userService.getUsersByUserName(userName);
            if (users.isEmpty()) {
                return new ResponseEntity<>("No users found with the specified username", HttpStatus.NOT_FOUND);
            }
        } else if (email != null) {
            users = userService.getUsersByEmail(email);
            if (users.isEmpty()) {
                return new ResponseEntity<>("No users found with the specified email", HttpStatus.NOT_FOUND);
            }
        } else if (phone != null) {
            users = userService.getUsersByPhone(phone);
            if (users.isEmpty()) {
                return new ResponseEntity<>("No users found with the specified phone number", HttpStatus.NOT_FOUND);
            }
        } else if (age != null) {
            return userService.getUsersByAge(age);
        } else {
            users = userService.getAllUsers();
            if (users.isEmpty()) {
                return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
