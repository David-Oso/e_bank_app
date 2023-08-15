package com.bank.E_Bank_App.config.security.services;

import com.bank.E_Bank_App.config.security.user.SecuredUser;
import com.bank.E_Bank_App.data.model.AppUser;
import com.bank.E_Bank_App.data.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EBankUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final AppUser appUser = appUserRepository.findAppUserByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User with this email not found!"));
        return new SecuredUser(appUser);
    }
}
