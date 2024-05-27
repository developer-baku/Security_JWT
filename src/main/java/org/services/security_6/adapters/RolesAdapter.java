package org.services.security_6.adapters;

import lombok.Builder;
import org.services.security_6.entity.Roles;
import org.springframework.security.core.GrantedAuthority;
@Builder
public class RolesAdapter implements GrantedAuthority {
    private final Roles roles;

    public RolesAdapter(Roles roles) {
        this.roles = roles;
    }

    @Override
    public String getAuthority() {
        return roles.getRole();
    }
}
