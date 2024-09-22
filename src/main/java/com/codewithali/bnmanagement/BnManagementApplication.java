package com.codewithali.bnmanagement;

import com.codewithali.bnmanagement.model.Role;
import com.codewithali.bnmanagement.model.User;
import com.codewithali.bnmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.codewithali.bnmanagement.repository")
public class BnManagementApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public BnManagementApplication(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(BnManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> user = userRepository.findByEmail("info@bilisimnoktasi.com.tr");
        if(user.isEmpty()) {
            userRepository.save(new User(
                    "İbrahim",
                    "info@bilisimnoktasi.com.tr",
                    passwordEncoder.encode("479408Aa"),
                    null,
                    Set.of(Role.ADMIN)
            ));
        }

    }
}
