package com.bank.E_Bank_App.otp;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.exception.E_BankException;
import com.bank.E_Bank_App.utils.E_BankUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;

    @Override
    public String generateAndSaveOtp(Customer customer) {
        OtpEntity existingOtp = otpRepository.findMyTokenByCustomer(customer);
        if(existingOtp != null)
            otpRepository.delete(existingOtp);
        String generatedOtp = E_BankUtils.generateOtp();
        OtpEntity otpEntity = OtpEntity.builder()
                .customer(customer)
                .token(generatedOtp)
                .build();
        otpRepository.save(otpEntity);
        return generatedOtp;
    }

    @Override
    public String generateAndSaveToken(Customer customer) {
        return null;
    }

    @Override
    public OtpEntity validateReceivedOtp(String otp) {
        OtpEntity otpEntity = otpRepository.findByToken(otp);
        if(otpEntity == null)
            throw new E_BankException("Otp is invalid");
        else if(otpEntity.getExpirationTime().isBefore(LocalDateTime.now())){
            otpRepository.delete(otpEntity);
            throw new E_BankException("Otp is expired");
        }
        return otpEntity;
    }

    @Override
    public void deleteToken(OtpEntity otpEntity) {
        otpRepository.delete(otpEntity);
    }
}
