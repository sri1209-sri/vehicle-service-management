package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.dto.AuthRequest;
import com.wip.VehicleServiceManagement.dto.AuthResponse;
import com.wip.VehicleServiceManagement.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling user authentication requests and issuing JWTs.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management", description = "Endpoints for user login and JWT token generation")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Authenticate user credentials and return a JSON Web Token.
     *
     * @param authRequest login payload containing username and password
     * @return AuthResponse enclosing the JWT token
     * @throws Exception if credentials verification fails
     */
    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Validates the user credentials and generates a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials supplied")
            }
    )
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new AuthResponse(jwt);
    }
}
