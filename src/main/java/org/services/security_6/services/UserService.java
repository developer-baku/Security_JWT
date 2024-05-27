package org.services.security_6.services;

import lombok.AllArgsConstructor;
import org.services.security_6.adapters.UserDetailsAdapter;
import org.services.security_6.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;


    public UserDetailsAdapter getUserDetails(String username){
         return new UserDetailsAdapter(usersRepository.findByUsername(username).get()) ;
    }


}
