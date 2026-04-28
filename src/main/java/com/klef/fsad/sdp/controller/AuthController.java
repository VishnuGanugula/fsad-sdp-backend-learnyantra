package com.klef.fsad.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.dto.AuthRequestDTO;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController 
{
    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) 
    {
        try 
        {
            UserDetails userDetails = service.loadUserByUsernameAndRole(request.getLogin(), request.getRole());

            String role = userDetails.getAuthorities()
                    .iterator().next().getAuthority();

            // Match Password (now all are hashed via migration)
            boolean isValid = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());

            if (!isValid)
            {
                return ResponseEntity.status(401).body("Login Invalid");
            }

            // Generate JWT
            String token = jwtUtil.generateToken(userDetails);

            // Fetch full user object
            Object userObj = service.getUserByLoginAndRole(request.getLogin(), request.getRole());

            // RETURN user also
            return ResponseEntity.ok(
                Map.of(
                    "token", token,
                    "role", role,
                    "user", userObj   
                )
            );
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(401).body("User not found or invalid credentials");
        }
    }
}
