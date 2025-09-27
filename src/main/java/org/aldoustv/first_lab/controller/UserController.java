package org.aldoustv.first_lab.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aldoustv.first_lab.dto.request.UserRequest;
import org.aldoustv.first_lab.dto.response.UserResponse;
import org.aldoustv.first_lab.entity.User;
import org.aldoustv.first_lab.repository.UserRepository;
import org.aldoustv.first_lab.security.JwtService;
import org.aldoustv.first_lab.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.aldoustv.first_lab.entity.CustomDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/registry")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomDetails customDetails = (CustomDetails) authentication.getPrincipal();
        Long userId = customDetails.getId();
        String jwt = jwtService.generateToken(customDetails.getEmail(), userId);
        User user = userRepository.findByEmail(customDetails.getEmail()).orElseThrow();

        ResponseCookie jwtCookie = ResponseCookie.from("jwt",jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60*60)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        return ResponseEntity.ok(Map.of("Message", "Login successful"));
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@CookieValue(value = "jwt", required = false) String jwt, HttpServletResponse response){
//        if(jwt != null){
//            jwtService.extractToken(jwt).ifPresent(token -> jwtService.de)
//        }
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        return ResponseEntity.ok(Map.of("Message", "Logout successful"));
    }
}