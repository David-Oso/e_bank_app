package com.bank.E_Bank_App.config.security.filter;

import com.bank.E_Bank_App.config.security.jwtToken.EBankTokenRepository;
import com.bank.E_Bank_App.config.security.services.EBankUserDetailsService;
import com.bank.E_Bank_App.config.security.services.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
public class EBankAuthorizationFilter extends OncePerRequestFilter {
    private final EBankUserDetailsService eBankUserDetailsService;
    private final EBankTokenRepository eBankTokenRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String bearer = "Bearer ";
        if(StringUtils.hasText(authHeader) &&
                StringUtils.startsWithIgnoreCase(authHeader, bearer)){
            final String jwtToken = authHeader.substring(bearer.length());
            final String userEmail = jwtService.extractUsername(jwtToken);
            boolean isTokenValid = eBankTokenRepository.findJwtTokenByToken(jwtToken)
                    .map(token -> !token.isExpired() && !token.isRevoked())
                    .orElse(false);
            if(jwtService.isValidToken(jwtToken, userEmail) && isTokenValid && userEmail != null){
                UserDetails userDetails =
                        eBankUserDetailsService.loadUserByUsername(userEmail);
                final UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
