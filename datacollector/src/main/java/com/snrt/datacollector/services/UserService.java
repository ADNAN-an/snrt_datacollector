package com.snrt.datacollector.services;

import com.snrt.datacollector.exceptions.UserAlreadyExistsException;
import com.snrt.datacollector.models.User;
import com.snrt.datacollector.repositories.UserRepository;
import com.snrt.datacollector.types.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createSuperAdmin(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("SuperAdmin with this email already exists.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.SUPERADMIN);
        return userRepository.save(user);
    }


    @PreAuthorize("hasRole('SUPERADMIN')")
    public User createAdmin(String email, String password, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
        User admin = new User();
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(role);
        return userRepository.save(admin);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('SUPERADMIN')")
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
