package ru.home.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.home.service.JwtProvider;
import ru.home.web.dto.AuthDto;
import ru.home.web.dto.JwtToken;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody AuthDto authDto) {
        String username = authDto.getUsername().trim().toLowerCase();
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(username, authDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(jwtToken);
    }
}
