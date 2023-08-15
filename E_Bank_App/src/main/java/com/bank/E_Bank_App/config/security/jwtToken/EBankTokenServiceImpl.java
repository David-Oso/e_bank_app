package com.bank.E_Bank_App.config.security.jwtToken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EBankTokenServiceImpl implements EBankTokenService{
    private final EBankTokenRepository eBankTokenRepository;

    @Override
    public void saveToken(EBankToken eBankToken) {
        eBankTokenRepository.save(eBankToken);
    }

    @Override
    public Optional<EBankToken> getValidTokenByAnyToken(String anyToken) {
        return eBankTokenRepository.findJwtTokenByToken(anyToken);
    }

    @Override
    public void revokeToken(String accessToken) {
        final EBankToken eBankToken = getValidTokenByAnyToken(accessToken)
                .orElse(null);
        if(eBankToken != null){
            eBankToken.setRevoked(true);
            eBankTokenRepository.save(eBankToken);
        }
    }
}
