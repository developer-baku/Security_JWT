package org.services.security_6.controller;

import org.services.security_6.components.LoginRequest;
import org.services.security_6.components.LoginResponse;
import org.services.security_6.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyController {
     @Autowired
    private JwtUtils utils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/today")
    public String z() {
        return "Present.......";
    }
    @GetMapping("/tomorrow")
    public String a() {
        return "Future........";
    }

    @GetMapping("/yesterday")
    public String q() {
        return "Past........";
    }

//    @PreAuthorize()
//    @PostAuthorize()
    @GetMapping("/admin")
    public String w() {
        return "Admin Only";
    }

    @GetMapping("/hello")
    public String any() {
        return "Welcome!";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            System.out.println(loginRequest.getUsername()+"-"+loginRequest.getPassword());
            authentication =  authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        }catch (AuthenticationException e){
            Map<String, Object> map = new HashMap<>();
            map.put("status", false);
            map.put("message", "Bad credentials");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetail= (UserDetails) authentication.getPrincipal();
        String jwtToken = utils.generateTokenFromUsername(userDetail);
        List<String> roles = userDetail.getAuthorities().stream().map(
                GrantedAuthority::getAuthority
        ).toList();
        LoginResponse response = new LoginResponse(jwtToken,roles,userDetail.getUsername());
        return ResponseEntity.ok(response);
    }

}
