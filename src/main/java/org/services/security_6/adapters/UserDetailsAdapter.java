package org.services.security_6.adapters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.services.security_6.entity.Roles;
import org.services.security_6.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
public class UserDetailsAdapter implements UserDetails {
    private final Users users;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RolesAdapter> list= new ArrayList<>();
        for (Roles roles1 : users.getRoles()){
            RolesAdapter rolesAdapter = new RolesAdapter(roles1);
            list.add(rolesAdapter);
        }
        return list;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
