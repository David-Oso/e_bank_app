package com.bank.E_Bank_App.service.appUser;

import com.bank.E_Bank_App.data.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class AppUserServiceImplTest {
    @Autowired AppUserService appUserService;

    @Test
    void authenticate() {
        AppUser appUser = appUserService.authenticate("osodavid001@gmail.com", "Password123$");
        assertThat(appUser).isNotNull();
    }
}