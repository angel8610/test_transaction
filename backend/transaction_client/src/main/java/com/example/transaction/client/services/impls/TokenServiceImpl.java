package com.example.transaction.client.services.impls;

import com.example.transaction.client.configurations.SecurityJwtConfiguration;
import com.example.transaction.client.dtos.AuthenticatedUserDTO;
import com.example.transaction.client.services.TokenService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenServiceImpl implements TokenService {

  private final JwtEncoder jwtEncoder;
  private final SecurityJwtConfiguration securityJwtConfiguration;

  public TokenServiceImpl(JwtEncoder jwtEncoder, SecurityJwtConfiguration securityJwtConfiguration) {
    this.jwtEncoder = jwtEncoder;
    this.securityJwtConfiguration = securityJwtConfiguration;
  }

  @Override
  public String generateToken(AuthenticatedUserDTO user) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer(securityJwtConfiguration.getIssuer())
      .subject(user.userId())
      .issuedAt(now)
      .expiresAt(now.plusSeconds(securityJwtConfiguration.getExpiration()))
      .claim("username", user.username())
      .claim("roles", user.roles())
      .build();

    JwsHeader header = JwsHeader
      .with(MacAlgorithm.HS256)
      .keyId("jwt-key-1")
      .build();

    return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
      .getTokenValue();
  }


}
