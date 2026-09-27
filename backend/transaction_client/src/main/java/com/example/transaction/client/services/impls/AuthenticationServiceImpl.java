package com.example.transaction.client.services.impls;

import com.example.transaction.client.configurations.SecurityJwtConfiguration;
import com.example.transaction.client.dtos.AuthenticatedUserDTO;
import com.example.transaction.client.dtos.LoginRequestDTO;
import com.example.transaction.client.dtos.LoginResponseDTO;
import com.example.transaction.client.services.AuthenticationService;
import com.example.transaction.client.services.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;
  private final SecurityJwtConfiguration securityJwtConfiguration;

  public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                   TokenService tokenService,
                                   SecurityJwtConfiguration securityJwtConfiguration) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
    this.securityJwtConfiguration = securityJwtConfiguration;
  }

  @Override
  public LoginResponseDTO authenticate(LoginRequestDTO request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    Set<String> roles = authentication.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toUnmodifiableSet());

    AuthenticatedUserDTO user = new AuthenticatedUserDTO(
      authentication.getName(),
      authentication.getName(),
      roles
    );

    var token = tokenService.generateToken(user);
    return new LoginResponseDTO(token, "Bearer", securityJwtConfiguration.getExpiration());
  }


}
