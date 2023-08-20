package com.bank.E_Bank_App.service.appUser;

import com.bank.E_Bank_App.config.security.jwtToken.EBankToken;
import com.bank.E_Bank_App.config.security.jwtToken.EBankTokenService;
import com.bank.E_Bank_App.config.security.services.JwtService;
import com.bank.E_Bank_App.data.model.AppUser;
import com.bank.E_Bank_App.data.repository.AppUserRepository;
import com.bank.E_Bank_App.dto.response.JwtResponse;
import com.bank.E_Bank_App.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService{
    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EBankTokenService eBankTokenService;

    @Override
    public AppUser authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        String userEmail = authentication.getName();
        return getAppUserByEmail(userEmail);
    }

    private AppUser getAppUserByEmail(String email) {
        return appUserRepository.findAppUserByEmail(email).orElseThrow(
                ()-> new NotFoundException("User not found"));
    }

    @Override
    public JwtResponse getJwtTokenResponse(AppUser user){
        final String email = user.getEmail();
        final String accessToken = jwtService.generateAccessToken(email);
        final String refreshToken = jwtService.generateRefreshToken(email);
        saveEBankToken(user, accessToken);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveEBankToken(AppUser user, String accessToken) {
        final EBankToken eBankToken = EBankToken.builder()
                .token(accessToken)
                .appUser(user)
                .isExpired(false)
                .isRevoked(false)
                .build();
        eBankTokenService.saveToken(eBankToken);
    }
}
