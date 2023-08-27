package com.bank.E_Bank_App.service.admin;

import com.bank.E_Bank_App.config.adminConfig.AdminConfig;
import com.bank.E_Bank_App.data.model.Admin;
import com.bank.E_Bank_App.data.model.AppUser;
import com.bank.E_Bank_App.data.model.Role;
import com.bank.E_Bank_App.data.repository.AdminRepository;
import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.response.JwtResponse;
import com.bank.E_Bank_App.dto.response.LoginResponse;
import com.bank.E_Bank_App.exception.InvalidDetailsException;
import com.bank.E_Bank_App.exception.NotFoundException;
import com.bank.E_Bank_App.service.appUser.AppUserService;
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
    private final AppUserService appUserService;

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
        AppUser appUser = appUserService
                .authenticate(request.getEmail(), request.getPassword());
        JwtResponse jwtResponse = appUserService.getJwtTokenResponse(appUser);
        Admin admin = getAdminByEmail(appUser.getEmail());
        if(admin.getIdentity().equals(request.getIdentity())){
            return LoginResponse.builder()
                    .jwtResponse(jwtResponse)
                    .build();
        }
        throw new InvalidDetailsException("Incorrect identity entered");
    }

    private Admin getAdminByEmail(String email){
        return adminRepository.findAdminByAppUser_Email(email).orElseThrow(
                ()-> new NotFoundException("Admin with this email not found"));
    }
}
