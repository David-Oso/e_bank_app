package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.repository.CustomerRepository;
import com.bank.E_Bank_App.dto.request.AuthenticationRequest;
import com.bank.E_Bank_App.dto.request.EmailVerificationRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import com.bank.E_Bank_App.exception.E_BankException;
import com.bank.E_Bank_App.exception.NotFoundException;
import com.bank.E_Bank_App.service.CustomerService;
import com.bank.E_Bank_App.service.MailService;
import com.bank.E_Bank_App.utils.E_BankUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MailService mailService;
    private final ModelMapper modelMapper;
//    private final PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        checkIfEmailAlreadyExists(request.getEmail());
        Customer savedCustomer = getSavedCustomer(request);
        String token = E_BankUtils.generateRandomString(5);
        int age = validateAge(savedCustomer.getDateOfBirth());
        if (age == 0){
            savedCustomer.setLocked(true);
            customerRepository.save(savedCustomer);
            throw new E_BankException("Cannot register this account because you are less than 16 years old");
        }
        sendVerificationMail(savedCustomer, token);
        return RegisterResponse.builder()
                .message("Check your mail for verification token to activate your account")
                .isSuccess(true)
                .build();
    }

    @Override
    public String verifyEmail(EmailVerificationRequest request) {
        Customer registeredCustomer = getCustomerByEmail(request.getEmail());
        if(!registeredCustomer.isLocked()){

        }
        return null;
    }

    private void checkIfEmailAlreadyExists(String email) {
        boolean isPresent = customerRepository.findByEmail(email).isPresent();
            if(isPresent){
                Customer customer = customerRepository.findByEmail(email).get();
                if(!customer.isEnable())resendVerificationMail(customer);
                else if (customer.isLocked())
                    throw new E_BankException("Account has been locked for some time");
            }
    }

    public void resendVerificationMail(Customer customer) {
        String token = E_BankUtils.generateRandomString(5);
        sendVerificationMail(customer, token);
    }

    private Customer getSavedCustomer(RegisterRequest request) {
        Customer customer = modelMapper.map(request, Customer.class);
        LocalDate dateOfBirth = convertDateOBirthToLocalDate(request.getDateOfBirth());
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        customer.setPassword(encodedPassword);
        customer.setPassword(request.getPassword());
        customer.setDateOfBirth(dateOfBirth);
        return customerRepository.save(customer);
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                ()-> new NotFoundException("Customer not found"));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("Customer with this email not found"));
    }

    private LocalDate convertDateOBirthToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
    private int validateAge(LocalDate date) {
        int years = Period.between(date, LocalDate.now()).getYears();
        if(years < 16)return 0;
        else return years;
    }

    private void sendVerificationMail(Customer customer, String token){
        String mailTemplate = E_BankUtils.GET_EMAIL_VERIFICATION_MAIL_TEMPLATE;
        String firstName = customer.getFirstName();
        String htmlContent = String.format(mailTemplate, firstName, token);
        String subject = "Email Verification";
        mailService.sendHtmlMail(firstName, customer.getEmail(), subject, htmlContent);
    }
}
