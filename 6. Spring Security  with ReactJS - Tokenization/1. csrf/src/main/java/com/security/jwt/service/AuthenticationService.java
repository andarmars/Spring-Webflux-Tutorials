package com.security.jwtAuth.service;

package com.security.jwt.service;

import com.codersee.jwtauth.config.JwtProperties;
import com.codersee.jwtauth.controller.auth.AuthenticationRequest;
import com.codersee.jwtauth.controller.auth.AuthenticationResponse;
import com.codersee.jwtauth.repository.RefreshTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationService(AuthenticationManager authManager,
                                   CustomUserDetailsService userDetailsService,
                                   TokenService tokenService,
                                   JwtProperties jwtProperties,
                                   RefreshTokenRepository refreshTokenRepository) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.jwtProperties = jwtProperties;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        UserDetails user = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);

        refreshTokenRepository.save(refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public String refreshAccessToken(String refreshToken) {
        String extractedEmail = tokenService.extractEmail(refreshToken);

        if (extractedEmail != null) {
            UserDetails currentUserDetails = userDetailsService.loadUserByUsername(extractedEmail);
            UserDetails refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken);

            if (!tokenService.isExpired(refreshToken) && refreshTokenUserDetails != null
                    && refreshTokenUserDetails.getUsername().equals(currentUserDetails.getUsername())) {
                return createAccessToken(currentUserDetails);
            }
        }
        return null;
    }

    private String createAccessToken(UserDetails user) {
        return tokenService.generate(user, getAccessTokenExpiration());
    }

    private String createRefreshToken(UserDetails user) {
        return tokenService.generate(user, getRefreshTokenExpiration());
    }

    private Date getAccessTokenExpiration() {
        return new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration());
    }

    private Date getRefreshTokenExpiration() {
        return new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration());
    }
}

