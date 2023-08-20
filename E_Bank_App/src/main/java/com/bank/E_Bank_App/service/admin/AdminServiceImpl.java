package com.bank.E_Bank_App.service.admin;

import com.bank.E_Bank_App.config.adminConfig.AdminConfig;
import com.bank.E_Bank_App.data.model.Admin;
import com.bank.E_Bank_App.data.model.AppUser;
import com.bank.E_Bank_App.data.model.Role;
import com.bank.E_Bank_App.data.repository.AdminRepository;
import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.response.LoginResponse;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminConfig adminConfig;
    private final PasswordEncoder passwordEncoder;

//    @PostConstruct
    public void registerAdmin(){
        final String encodedPassword = passwordEncoder.encode(adminConfig.getAdminPassword());
    AppUser appUser = AppUser.builder()
            .firstName(adminConfig.getAdminFirstName())
            .lastName(adminConfig.getAdminLastName())
            .phoneNumber(adminConfig.getAdminPhoneNumber())
            .email(adminConfig.getAdminEmail())
            .password(encodedPassword)
            .role(Role.ADMIN)
            .isLocked(false)
            .isEnable(true)
            .build();

    Admin admin = Admin.builder()
            .appUser(appUser)
            .identity(adminConfig.getAdminId())
            .build();
    adminRepository.save(admin);
    }


    @Override
    public LoginResponse authenticate(AdminLoginRequest request) {
        return null;
    }
}
