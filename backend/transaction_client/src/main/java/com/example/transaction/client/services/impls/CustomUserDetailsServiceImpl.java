package com.example.transaction.client.services.impls;

import com.example.transaction.client.repositories.UserRepository;
import com.example.transaction.client.services.CustomUserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    var authorities = user.getRoles().stream()
      .map(role -> new SimpleGrantedAuthority(role.getName()))
      .toList();
    return User
      .withUsername(user.getUsername())
      .password(user.getPassword())
      .authorities(authorities)
      .build();
  }


}
