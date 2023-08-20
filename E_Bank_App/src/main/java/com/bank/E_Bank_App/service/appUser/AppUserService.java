package com.bank.E_Bank_App.service.appUser;

import com.bank.E_Bank_App.data.model.AppUser;

public interface AppUserService {
    AppUser authenticate(String email, String password);
}
