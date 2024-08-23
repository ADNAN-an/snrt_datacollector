package com.snrt.datacollector.config;

import com.snrt.datacollector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializerConfig {

    private final UserService userService;

    @Autowired
    public DataInitializerConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            String superAdminEmail = "superadmin@example.com";
            String superAdminPassword = "SuperSecretPassword"; // Use a strong password and ideally get it from an environment variable

            try {
                // Attempt to create the SuperAdmin
                userService.createSuperAdmin(superAdminEmail, superAdminPassword);
                System.out.println("SuperAdmin user created: " + superAdminEmail);
            } catch (IllegalArgumentException e) {
                // Handle case where the SuperAdmin already exists
                System.out.println(e.getMessage());
            } catch (Exception e) {
                // Handle any other unexpected errors
                e.printStackTrace();
            }
        };
    }
}
