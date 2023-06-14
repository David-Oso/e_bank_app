//package com.bank.E_Bank_App.config.security;
//
//import com.bank.E_Bank_App.data.model.Customer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@RequiredArgsConstructor
//public class SecuredUser implements UserDetails {
//    private final Customer customer;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(customer.getRole().name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return customer.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return customer.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
