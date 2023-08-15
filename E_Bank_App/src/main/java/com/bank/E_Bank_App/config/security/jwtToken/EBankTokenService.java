package com.bank.E_Bank_App.config.security.jwtToken;

import java.util.Optional;

public interface EBankTokenService {
    void saveToken(EBankToken testingToken);
    Optional<EBankToken> getValidTokenByAnyToken(String anyToken);
    void revokeToken(String accessToken);
}
