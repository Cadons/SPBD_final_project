package ch.supsi.backend_api_rest.security;

import ch.supsi.backend_api_rest.model.EmployeeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails{

    private final EmployeeEntity employeeEntity;
    public SecurityUser(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(employeeEntity.isMenager())
            return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }

    @Override
    public String getPassword() {
        return employeeEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return employeeEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
