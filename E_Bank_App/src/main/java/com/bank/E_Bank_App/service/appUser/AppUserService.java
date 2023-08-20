package com.bank.E_Bank_App.service.appUser;

import com.bank.E_Bank_App.data.model.AppUser;
import com.bank.E_Bank_App.dto.response.JwtResponse;

public interface AppUserService {
    AppUser authenticate(String email, String password);
    JwtResponse getJwtTokenResponse(AppUser appUser);
}
