package com.bank.E_Bank_App.config.security.jwtToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EBankTokenRepository extends JpaRepository<EBankToken, Long> {
    @Query("""
           select t from EBankToken  t inner join AppUser appuser\s
           on t.appUser.id = appuser.id\s
           where appuser.id = :id and (t.isExpired = false or t.isRevoked = false)
           """)
    List<EBankToken> findAllValidTokenByUser(Long id);
    Optional<EBankToken> findJwtTokenByToken(String token);
}
