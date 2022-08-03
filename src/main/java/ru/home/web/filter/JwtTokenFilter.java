package ru.home.web.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.home.config.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var header = request.getHeader(AUTHORIZATION);
        // Get authorization header and validate
        if (isNull(header) || !header.startsWith("Bearer")) {
            doFilter(request, response, filterChain);
        }
        // Get jwt token and validate
        var token = header.split(" ")[1].trim();

        if (!jwtUtils.validateToken(token)) {
            doFilter(request, response, filterChain);
        }
        // Get user identity and set it on the spring security context
        String username = jwtUtils.getUsernameFromToken(token);
        log.info("JwtTokenFilter for user {}", username);
        var userDetails = userDetailsService.loadUserByUsername(username);
        if (isNull(userDetails)) {
            doFilter(request, response, filterChain);
        }

        var upat = new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(upat);
        log.info("User info successfully added in security context");
        doFilter(request, response, filterChain);
    }
}
