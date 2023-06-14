//package com.bank.E_Bank_App.service.implementations;
//
//import com.bank.E_Bank_App.config.security.JwtService;
//import com.bank.E_Bank_App.data.model.Account;
//import com.bank.E_Bank_App.data.model.Bank;
//import com.bank.E_Bank_App.data.model.Customer;
//import com.bank.E_Bank_App.data.repository.BankRepository;
//import com.bank.E_Bank_App.data.repository.CustomerRepository;
//import com.bank.E_Bank_App.dto.request.AuthenticationRequest;
//import com.bank.E_Bank_App.dto.request.RegisterUserRequest;
//import com.bank.E_Bank_App.dto.response.ApiResponse;
//import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
//import com.bank.E_Bank_App.dto.response.RegisterResponse;
//import com.bank.E_Bank_App.exception.*;
//import com.bank.E_Bank_App.service.MailService;
//import com.bank.E_Bank_App.service.CustomerService;
//import com.bank.E_Bank_App.utils.E_BankUtils;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.Period;
//import java.time.format.DateTimeFormatter;
//import java.util.Map;
//
//import static com.bank.E_Bank_App.utils.E_BankUtils.EMAIL_REGEX_STRING;
//
//@Service
//@RequiredArgsConstructor
//public class CustomerServiceImpl implements CustomerService {
//    private final BankRepository bankRepository;
//    private final CustomerRepository customerRepository;
//    private final MailService mailService;
//    private final SpringTemplateEngine templateEngine;
//    private final JwtService jwtService;
//    private final Bank bank = new Bank();
//
//
////    @Override
////    public String registerUser(EmailVerificationRequest request) {
////        EmailNotificationRequest notificationRequest = new EmailNotificationRequest();
////        String mailTemplate = E_BankUtils.getMailTemplate();
////        notificationRequest.setHtmlContent(String.format(mailTemplate, request.getEmail(), E_BankUtils.generateVerificationLink(0L)));
////        notificationRequest.getTo().add(new Recipient(request.getEmail(), request.getEmail()));
////        notificationRequest.setSubject("Registration Verification Link");
////        String response = mailService.sendHtmlMail(notificationRequest);
////        if(response != null)return  "Check your mail for the verification link to active you account";
////        throw new EBankFailureException("Registration failure");
////    }
//    @Override
//    public ApiResponse sendVerificationLink(
//            @NotEmpty(message = "field email cannot be empty")
//            @NotBlank(message = "field email cannot be blank")
//            @Email(message = "must be valid email address", regexp = EMAIL_REGEX_STRING)
//            String email) {
//        Customer customer = customerRepository.findByEmail(email);
//        if (customer != null && customer.isEnable())throw new AlreadyExistException("Customer with this email already exists");
//        else if (customer != null && !customer.isEnable()) return resendVerificationLink(email);
//        else {
//            Customer newCustomer = new Customer();
//            newCustomer.setEmail(email);
//            Customer savedCustomer = customerRepository.save(newCustomer);
//            sendVerificationMail(savedCustomer.getEmail());
//        }
//        return ApiResponse.builder()
//                .message("Check your email for the verification link to activate your E_Bank Account")
//                .isSuccess(true)
//                .build();
//    }
//
//
//    @Override
//    public ApiResponse resendVerificationLink(String email) {
//        sendVerificationMail(email);
//        return ApiResponse.builder()
//                .message("A new verification mail has been sent to your mail. Please check your mail to activate you E_Bank Account")
//                .isSuccess(true)
//                .build();
//    }
//
//    @Override
//    public RegisterResponse verifyAndRegisterAccount(RegisterUserRequest request) {
//        Customer newCustomer = getCustomerByEmail(request.getEmail());
//        if (!isValidToken(request.getJwtToken(), newCustomer))
//            throw new EBankFailureException("Account verification failed");
//        LocalDate dateOfBirth = convertDateOBirthToLocalDate(request.getDateOfBirth());
//        setCustomerDetails(request, newCustomer, dateOfBirth);
//        Customer savedCustomer = customerRepository.save(newCustomer);
//        Integer age = validateAge(savedCustomer.getDateOfBirth());
//        if (age != null)savedCustomer.setBlocked(false);
//        bank.getCustomers().add(savedCustomer);
//        bankRepository.save(bank);
//        return RegisterResponse.builder()
//                .message("Account Verification Successful")
//                .isRegistered(true)
//                .build();
//    }
//
//    private boolean isValidToken(String jwtToken, Customer newCustomer) {
//        return jwtService.isTokenValid(jwtToken, newCustomer);
//    }
//
//    private static void setCustomerDetails(RegisterUserRequest request, Customer newCustomer, LocalDate dateOfBirth) {
//        newCustomer.setFirstName(request.getFirstName());
//        newCustomer.setLastName(request.getLastName());
//        newCustomer.setPhoneNumber(request.getPhoneNumber());
//        newCustomer.setGender(request.getGender());
//        newCustomer.setDateOfBirth(dateOfBirth);
//        newCustomer.setPassword(request.getPassword());
//        newCustomer.setAccount(new Account());
//        newCustomer.setCreatedAt(LocalDateTime.now());
//        newCustomer.setEnable(true);
//    }
//
//    private int validateAge(LocalDate date) {
//        int years = Period.between(date, LocalDate.now()).getYears();
//        if (years < 16)throw new E_BankException("Account cannot people below 16 years old");
//        else if (years > 18) return years;
//        else throw new E_BankException("Error saving customer date ob birth");
//    }
//
//    private LocalDate convertDateOBirthToLocalDate(String date) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        return LocalDate.parse(date, formatter);
//    }
//
//
//    @Override
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        return null;
//    }
//
//    @Override
//    public Customer getCustomerById(Long customerId) {
//        return customerRepository.findById(customerId).orElseThrow(
//                ()-> new NotFoundException("Customer not found..."));
//    }
//
//    @Override
//    public Customer getCustomerByEmail(String email) {
//        return customerRepository.findCustomerByEmail(email).orElseThrow(
//                ()-> new NotFoundException("Customer with this email not found..."));
//    }
//
//
//    private void sendVerificationMail(String email) {
//        Context context = new Context();
//        String verificationLink = E_BankUtils.generateVerificationLink(email);
//        context.setVariables(Map.of("name", email, "link", verificationLink));
//        String htmlContent = templateEngine.process("emailVerificationMail", context);
//        mailService.sendHtml(email, email, "E_Bank Account Verification", htmlContent);
//    }
//}
