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
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("VIEWER"));
            roleRepository.save(new Role("CASHIER"));
            roleRepository.save(new Role("DIRECTOR"));
            roleRepository.save(new Role("ADMIN"));
        }

        // Create default admin user if no admin exists
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setEmail("admin@theater.ru");
            adminUser.setFullName("Administrator");
            adminUser.setPhone("+7 (999) 999-99-99");
            
            Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            adminUser.addRole(adminRole);
            
            userRepository.save(adminUser);
        }
    }
}

