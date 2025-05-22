package com.example.back2.service;

import com.example.back2.model.User;
import com.example.back2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public  User save(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String email) {
        if(findByEmail(email).isPresent()) {
            userRepository.delete(findByEmail(email).get());
        }else{ throw new RuntimeException("Kullanıcı bulunamadı: " + email);}
    }
}
