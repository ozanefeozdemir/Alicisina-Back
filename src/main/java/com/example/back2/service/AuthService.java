package com.example.back2.service;

import com.example.back2.dto.AuthenticationRequest;
import com.example.back2.dto.AuthenticationResponse;
import com.example.back2.dto.RegisterRequest;
import com.example.back2.dto.RegisterResponse;
import com.example.back2.model.Role;
import com.example.back2.model.User;
import com.example.back2.repository.UserRepository;
import com.example.back2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService( BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Kullanıcı Kayıt
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Bu email ile bir kullanıcı zaten mevcut.");
        }

        // Kullanıcı oluştur
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        //user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(Role.USER);  // Varsayılan olarak USER rolü atıyoruz

        userRepository.save(user);

        // JWT token üret
        String token = jwtUtil.generateToken(user.getEmail());

        // Kayıt başarılı olduğunda response döndür
        return new RegisterResponse("Kayıt başarılı", token);
    }

    // Kullanıcı Girişi (Login)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Kullanıcıyı email ile bul
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        /* Şifreyi kontrol et (FOR CRYPTED PASSWORD)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Geçersiz şifre");
        }*/

        //FOR UNCRYPTED PASSWORD
        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Geçersiz şifre");
        }

        // JWT token üret
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthenticationResponse(token);
    }
}
