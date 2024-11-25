package com.CRUD.Operations.Service;

import com.CRUD.Operations.Entity.User;
import com.CRUD.Operations.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public ResponseEntity<?> getUsersByAge(int age) {
        List<User> users = userRepository.findAll();

        if (age >= 18) {
            List<User> filteredUsers = users.stream()
                    .filter(user -> user.getAge() > 18)
                    .collect(Collectors.toList());

            if (!filteredUsers.isEmpty()) {
                return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No users found older than 18", HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>("Age must be 18 or older", HttpStatus.BAD_REQUEST);
    }


    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByPhone(Long phone) {
        return userRepository.findByPhone(phone);
    }

    public User updateUser(int userId, User userDetails) {
        logger.info("Updating user with ID: {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUserName(userDetails.getUserName());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setAge(userDetails.getAge());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhone(userDetails.getPhone());
            User updatedUser = userRepository.save(existingUser);
            logger.info("User updated successfully: {}", updatedUser);
            return updatedUser;
        } else {
            logger.error("User not found with id: {}", userId);
            throw new RuntimeException("User not found with id " + userId);
        }
    }
    public Map<User, ArrayList<String>> getUserData() {
        Map<User, ArrayList<String>> hashMap = new HashMap<>();

        List<User> users = userRepository.findAll();

        List<User> filteredUsers = users.stream()
                .filter(user -> user.getUserName().length() > 5)
                .toList();

        for (User user : filteredUsers) {
            ArrayList<String> userNames = new ArrayList<>();
            userNames.add(user.getUserName());
            hashMap.put(user, userNames);
        }

        return hashMap;
    }

}
