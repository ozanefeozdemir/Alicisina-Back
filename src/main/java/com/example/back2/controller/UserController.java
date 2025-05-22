package com.example.back2.controller;

import com.example.back2.model.User;
import com.example.back2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUserDetails(@PathVariable String email) {
        // Token'dan email alındığı için, email ile kullanıcıyı buluyoruz
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            userToUpdate.setEmail(email);
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setPhone(user.getPhone());
            userToUpdate.setAddress(user.getAddress());
        }
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
