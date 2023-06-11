package com.bank.E_Bank_App.customerService.implementations;

import com.bank.E_Bank_App.data.model.Bank;
import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.repository.BankRepository;
import com.bank.E_Bank_App.data.repository.CustomerRepository;
import com.bank.E_Bank_App.dto.request.EmailVerificationRequest;
import com.bank.E_Bank_App.dto.response.ApiResponse;
import com.bank.E_Bank_App.exception.EBankFailureException;
import com.bank.E_Bank_App.exception.NotFoundException;
import com.bank.E_Bank_App.customerService.MailService;
import com.bank.E_Bank_App.customerService.CustomerService;
import com.bank.E_Bank_App.utils.E_BankUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final MailService mailService;
    private final SpringTemplateEngine templateEngine;


//    @Override
//    public String registerUser(EmailVerificationRequest request) {
//        EmailNotificationRequest notificationRequest = new EmailNotificationRequest();
//        String mailTemplate = E_BankUtils.getMailTemplate();
//        notificationRequest.setHtmlContent(String.format(mailTemplate, request.getEmail(), E_BankUtils.generateVerificationLink(0L)));
//        notificationRequest.getTo().add(new Recipient(request.getEmail(), request.getEmail()));
//        notificationRequest.setSubject("Registration Verification Link");
//        String response = mailService.sendHtmlMail(notificationRequest);
//        if(response != null)return  "Check your mail for the verification link to active you account";
//        throw new EBankFailureException("Registration failure");
//    }
    @Override
    public ApiResponse registerUser(EmailVerificationRequest request) {
        Customer newCustomer = new Customer();
        newCustomer.setEmail(request.getEmail());
        Customer existingCustomer = customerRepository.findCustomerByEmail(request.getEmail());
        if(existingCustomer != null)
            resendVerificationLinkMail(newCustomer);
        else {
        Customer savedCustomer = customerRepository.save(newCustomer);
        String mailResponse = sendVerificationMail(savedCustomer);
        if(mailResponse == null)throw new EBankFailureException("Email verification failure");
        else addCustomerToBankCustomers(savedCustomer);
        }
        return ApiResponse.builder()
            .message("Check your email for the verification link to activate your E_Bank Account")
            .isSuccess(true)
            .build();
    }

    private void addCustomerToBankCustomers(Customer customer) {
        Bank bank = new Bank();
        bank.setCustomers(List.of(customer));
        bankRepository.save(bank);
    }

    @Override
    public void resendVerificationLinkMail(Customer customer) {
        Customer existingCustomer = getCustomerByEmail(customer.getEmail());
        if(existingCustomer != null && !existingCustomer.isEnable())
            sendVerificationMail(existingCustomer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                ()-> new NotFoundException("Customer not found..."));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if(customer == null)
            throw new NotFoundException(String.format("Customer with email %s not found...", email));
        else return customer;
    }


    private String sendVerificationMail(Customer savedCustomer) {
        Context context = new Context();
        String email = savedCustomer.getEmail();
        String verificationLink = E_BankUtils.generateVerificationLink(0L);
        context.setVariables(Map.of("name", email, "link", verificationLink));
        String htmlContent = templateEngine.process("emailVerificationMail", context);
        return mailService.sendHtml(email, email, "E_Bank Account Verification", htmlContent);
    }
}
