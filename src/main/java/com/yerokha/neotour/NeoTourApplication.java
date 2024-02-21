package com.yerokha.neotour;

import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Role;
import com.yerokha.neotour.repository.RoleRepository;
import com.yerokha.neotour.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class NeoTourApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeoTourApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.count() > 0) {
                return;
            }
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            Role userRole = roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);

            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setEnabled(true);
            admin.setAuthorities(roles);
            userRepository.save(admin);
        };
    }

}

































