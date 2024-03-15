package com.security.jwtAuth.oontroller.auth;

package com.security.jwt.controller.auth;

import com.codersee.jwtauth.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authRequest) {
        return authenticationService.authentication(authRequest);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        String token = authenticationService.refreshAccessToken(request.getToken());
        if (token != null) {
            return new TokenResponse(token);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.");
        }
    }

    private static class TokenResponse {
        private final String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        // Getter
        public String getToken() {
            return token;
        }
    }
}

