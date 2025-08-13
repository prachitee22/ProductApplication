package com.productapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userRepository.existsByEmail("admin@admin.com")) {
            User admin = new User();
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }
    }
}