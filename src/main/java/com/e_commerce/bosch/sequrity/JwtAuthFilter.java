package com.e_commerce.bosch.sequrity;

import com.e_commerce.bosch.entities.Role;
import com.e_commerce.bosch.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.Column;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();


        if (path.startsWith("/api/auth/")) return true;      // register/login javno
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) return true;
        if ("GET".equalsIgnoreCase(method) && path.startsWith("/api/products")) return true;

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeaderNotExists(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            Claims claims = getClaimsFromAuthHeader(authHeader);

            Long userId = claims.get("userId", Number.class).longValue();
            String username = claims.getSubject();
            Role role = Role.valueOf(claims.get("role", String.class));
            String roleFull = "ROLE_" + role.name();

            Principal principal = new JwtPrincipal(userId, username, role);
            var authToken = new UsernamePasswordAuthenticationToken(
                    principal, null, List.of(new SimpleGrantedAuthority(roleFull)));

            SecurityContextHolder.getContext().setAuthentication(authToken);


            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {

            unauthorized(response, "token_expired", "JWT token has expired.");
        } catch (JwtException ex) {

            unauthorized(response, "invalid_token", "Invalid JWT token.");
        } catch (Exception ex) {

            unauthorized(response, "invalid_token", "Could not verify JWT token.");
        }
    }

    public boolean authHeaderNotExists(String authHeader) {
        return authHeader != null && !authHeader.startsWith("Bearer ");
    }

    public Claims getClaimsFromAuthHeader(String authHeader) {
        String token = authHeader.substring(7);
        return JwtUtil.extractAllClaims(token);
    }

    private void unauthorized(HttpServletResponse res, String error, String description) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setHeader("WWW-Authenticate",
                "Bearer error=\"" + error + "\", error_description=\"" + description + "\"");
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String body = "{\"error\":\"" + error + "\",\"error_description\":\"" + description + "\"}";
        res.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
    }
}
