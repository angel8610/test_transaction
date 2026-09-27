package com.example.transaction.client.configurations;

import com.example.transaction.client.entities.RoleEntity;
import com.example.transaction.client.entities.UserEntity;
import com.example.transaction.client.repositories.RoleRepository;
import com.example.transaction.client.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializerConfiguration {

  @Bean
  CommandLineRunner dataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
    return args -> {
      if (userRepository.count() == 0) {
        var user = UserEntity.builder()
          .password("$2a$12$qZI7g0QuT5O5kEpkpulD6uEkplRtXs/j3r78FuRwjR0yJGSf/gKES")
          .username("testUser")
          .build();
        var newUser = userRepository.save(user);

        var role = RoleEntity.builder()
          .description("Administrator role")
          .name("ROLE_ADMIN")
          .userId(newUser.getUserId())
          .build();
        roleRepository.save(role);
      }
    };

  }


}
