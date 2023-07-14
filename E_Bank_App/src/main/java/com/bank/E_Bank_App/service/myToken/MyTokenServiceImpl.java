package com.bank.E_Bank_App.service.myToken;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.MyToken;
import com.bank.E_Bank_App.data.repository.MyTokenRepository;
import com.bank.E_Bank_App.exception.E_BankException;
import com.bank.E_Bank_App.exception.InvalidDetailsException;
import com.bank.E_Bank_App.utils.E_BankUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
@AllArgsConstructor
public class MyTokenServiceImpl implements MyTokenService {
    private final MyTokenRepository myTokenRepository;
    @Override
    public String generateAndSaveMyToken(Customer customer) {
        Optional<MyToken> existingToken = myTokenRepository.findMyTokenByCustomer(customer);
        existingToken.ifPresent(myTokenRepository::delete);
        String generateToken = E_BankUtils.generateRandomString(6);
        MyToken myToken = MyToken.builder()
                .customer(customer)
                .token(generateToken)
                .build();
        myTokenRepository.save(myToken);
        return generateToken;
    }

    @Override
    public Optional<MyToken> validateReceivedToken(String token) {
        Optional<MyToken> myToken = myTokenRepository.findByToken(token);
        if(myToken.isEmpty())throw new InvalidDetailsException("Invalid Verification Token");
        else if(myToken.get().getExpirationTime().isBefore(LocalDateTime.now())){
            myTokenRepository.delete(myToken.get());
            throw new E_BankException("Token is expired");
        }
        return myToken;
    }

    @Override
    public void deleteToken(MyToken myToken) {
        myTokenRepository.delete(myToken);
    }
}
