package com.pm.spring.ema.securitycommon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final ObjectProvider<JwtDecoder> keycloakJwtDecoderProvider;

    public JwtAuthenticationFilter(ObjectProvider<JwtDecoder> keycloakJwtDecoderProvider) {
        this.keycloakJwtDecoderProvider = keycloakJwtDecoderProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("--- [JwtAuthenticationFilter] ---");
        System.out.println("Request URI: " + request.getRequestURI());

        String token = getTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            if (isKeycloakToken(token)) {
                System.out.println("Key cloak token received");
                authenticateWithKeycloakJwtIfPossible(token, request);
            } else if (validateToken(token)) {
                System.out.println("Normal token received");
                String username = getUsernameFromToken(token);
                UserDetails userDetails = new User(username, "", Collections.emptyList());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("Token is INVALID.");
            }
        } else {
            System.out.println("No token found in request.");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isKeycloakToken(String token) {
        String alg = getJwtHeaderValue(token);
        return StringUtils.hasText(alg) && alg.startsWith("RS");
    }

    private void authenticateWithKeycloakJwtIfPossible(String rawToken, HttpServletRequest request) {
        JwtDecoder jwtDecoder = keycloakJwtDecoderProvider.getIfAvailable();
        System.out.println(jwtDecoder);
        if (jwtDecoder == null) {
            request.setAttribute("not_authorized", true);
            System.out.println("Failed authorization : line 92");
            return;
        }

        try {
            var jwt = jwtDecoder.decode(rawToken);
            String email = jwt.getClaimAsString("email");
            String preferredUsername = jwt.getClaimAsString("preferred_username");
            System.out.println("Preferred username: " + preferredUsername);

            if (!StringUtils.hasText(email) && !StringUtils.hasText(preferredUsername)) {
                request.setAttribute("not_authorized", true);
                System.out.println("Failed authorization : line 103");
                return;
            }

            UserDetails userDetails = new User(preferredUsername, "", Collections.emptyList());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtException ex) {
            System.out.println("Failed authorization : line 115");
            request.setAttribute("not_authorized", true);
        }
    }

    private String getJwtHeaderValue(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return null;
            }
            byte[] decoded = Base64.getUrlDecoder().decode(parts[0]);
            String json = new String(decoded, StandardCharsets.UTF_8);
            Map<String, Object> values = OBJECT_MAPPER.readValue(json, new TypeReference<>() {});
            Object value = values.get("alg");
            return value == null ? null : String.valueOf(value);
        } catch (Exception ignored) {
            return null;
        }
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            System.out.println("Found token in Authorization header.");
            return bearerToken.substring(7);
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    System.out.println("Found token in cookie.");
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(token);
            return true;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    private String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
