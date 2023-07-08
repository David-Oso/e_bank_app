package com.bank.E_Bank_App.service.admin;

import com.bank.E_Bank_App.data.model.Admin;
import com.bank.E_Bank_App.data.model.AppUser;
import com.bank.E_Bank_App.data.model.Role;
import com.bank.E_Bank_App.data.repository.AdminRepository;
import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;

    @Value("${adminId}")
    private String adminId;

    @Value("${adminFirstName}")
    private String adminFirstName;

    @Value("${adminLastName}")
    private String adminLastName;

    @Value("${adminPhoneNumber}")
    private String adminPhoneNumber;

    @Value("${adminEmail}")
    private String adminEmail;

    @Value("${adminPassword}")
    private String adminPassword;


    @PostConstruct
    public void registerAdmin(){
    AppUser appUser = AppUser.builder()
            .firstName(adminFirstName)
            .lastName(adminLastName)
            .phoneNumber(adminPhoneNumber)
            .email(adminEmail)
            .password(adminPassword)
            .role(Role.ADMIN)
            .isLocked(false)
            .isEnable(true)
            .build();

    Admin admin = Admin.builder()
            .appUser(appUser)
            .identity(adminId)
            .build();
    adminRepository.save(admin);
    }


    @Override
    public AuthenticationResponse login(AdminLoginRequest request) {
        return null;
    }
}
