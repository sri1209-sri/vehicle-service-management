package com.wip.VehicleServiceManagement.config;

import com.wip.VehicleServiceManagement.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that intercepts incoming HTTP requests, parses JWT tokens from the Authorization header,
 * validates them, and authenticates the user within the Spring Security Context.
 * @author Devadarshini M
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Try to load authentication from the HTTP session first (for browser UI requests)
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        if (session != null) {
            Object contextObj = session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (contextObj instanceof org.springframework.security.core.context.SecurityContext) {
                org.springframework.security.core.context.SecurityContext context = 
                    (org.springframework.security.core.context.SecurityContext) contextObj;
                if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated()) {
                    SecurityContextHolder.setContext(context);
                }
            }
        }

        final String requestTokenHeader = request.getHeader("Authorization");
        final String referer = request.getHeader("Referer");

        String username = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token". Remove "Bearer " prefix to obtain the token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
            } catch (Exception e) {
                logger.warn("JWT Token has expired or is invalid");
            }
        } else if (referer != null && (referer.contains("/swagger-ui") || referer.contains("/api-docs"))) {
            // Auto-authenticate Swagger UI requests as 'admin' in development
            username = "admin";
        }

        // Once we get the token, validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            boolean isValid = false;
            if (jwtToken != null) {
                isValid = jwtTokenUtil.validateToken(jwtToken, userDetails);
            } else if ("admin".equals(username) && referer != null && (referer.contains("/swagger-ui") || referer.contains("/api-docs"))) {
                isValid = true;
            }

            // If token is valid, configure Spring Security to manually set authentication
            if (isValid) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
