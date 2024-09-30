package com.codewithali.bnmanagement.service;

import com.codewithali.bnmanagement.dto.UserDto;
import com.codewithali.bnmanagement.dto.request.CreateUserRequest;
import com.codewithali.bnmanagement.dto.request.LoginRequest;
import com.codewithali.bnmanagement.dto.response.SuccessReponse;
import com.codewithali.bnmanagement.dto.response.TokenResponse;
import com.codewithali.bnmanagement.exception.UserNotFoundException;
import com.codewithali.bnmanagement.model.User;
import com.codewithali.bnmanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse authenticate(LoginRequest req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(),req.getHashedPassword())
            );
            if(auth.isAuthenticated()) {
                return new TokenResponse(
                        jwtService.generateToken(addClaims(req.getEmail()),req.getEmail())
                );
            }else {
                throw new UserNotFoundException("User not found");
            }
        }
        throw new UserNotFoundException("User not found");
    }

    public UserDto getUser(String id) {
        return userRepository.findById(id).map(UserDto::convertToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public SuccessReponse registerUser(CreateUserRequest req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) {
            throw new UserNotFoundException("Kullanıcı zaten kayıtlı !");
        }else {
            User newUser = new User(req.getName(), req.getEmail(), passwordEncoder.encode(req.getHashedPassword()) );
            userRepository.save(newUser);
            return new SuccessReponse("Kullanıcı Oluşturuldu", HttpStatus.CREATED.value());
        }
    }

    private Map<String,Object> addClaims(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.get().getId());
        claims.put("role",user.get().getRoles());
        return claims;
    }

}
