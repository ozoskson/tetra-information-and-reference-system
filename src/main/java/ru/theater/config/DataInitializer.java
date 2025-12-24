package ru.theater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.theater.entity.Role;
import ru.theater.entity.User;
import ru.theater.repository.RoleRepository;
import ru.theater.repository.UserRepository;

import java.util.List;

@Component
public class DataInitializer {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        ensureRolesExist(List.of("VIEWER", "CASHIER", "DIRECTOR", "ADMIN"));

        createUserIfMissing(
            "admin",
            "admin123",
            "admin@theater.ru",
            "Administrator",
            "+7 (999) 999-99-99",
            "ADMIN"
        );

        createUserIfMissing(
            "director",
            "director123",
            "director@theater.ru",
            "Director",
            "+7 (900) 111-11-11",
            "DIRECTOR"
        );

        createUserIfMissing(
            "cashier",
            "cashier123",
            "cashier@theater.ru",
            "Cashier",
            "+7 (900) 222-22-22",
            "CASHIER"
        );

        createUserIfMissing(
            "viewer",
            "viewer123",
            "viewer@theater.ru",
            "Viewer",
            "+7 (900) 333-33-33",
            "VIEWER"
        );

        createUserIfMissing(
            "viewer2",
            "viewer123",
            "viewer2@theater.ru",
            "Viewer Two",
            "+7 (900) 444-44-44",
            "VIEWER"
        );
    }

    private void ensureRolesExist(List<String> roleNames) {
        for (String roleName : roleNames) {
            roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
        }
    }

    private void createUserIfMissing(
        String username,
        String rawPassword,
        String email,
        String fullName,
        String phone,
        String... roleNames
    ) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhone(phone);

        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException(roleName + " role not found"));
            user.addRole(role);
        }

        userRepository.save(user);
    }
}
