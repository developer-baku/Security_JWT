package org.services.security_6.components;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class LoginResponse {
    private String jwtToken;
    private String username;
    private List<String> roles;
    public LoginResponse(String jwtToken,List<String> roles, String username){
        this.jwtToken = jwtToken;
        this.username = username;
                this.roles=roles;
    }
}
