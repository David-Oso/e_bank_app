package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.data.model.*;
import com.bank.E_Bank_App.data.repository.CustomerRepository;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import com.bank.E_Bank_App.exception.E_BankException;
import com.bank.E_Bank_App.exception.InvalidDetailsException;
import com.bank.E_Bank_App.exception.NotFoundException;
import com.bank.E_Bank_App.service.CustomerService;
import com.bank.E_Bank_App.service.MailService;
import com.bank.E_Bank_App.service.MyTokenService;
import com.bank.E_Bank_App.utils.E_BankUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MailService mailService;
    private final MyTokenService myTokenService;
    private final ModelMapper modelMapper;
//    private final PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        checkIfEmailAlreadyExists(request.getEmail());
        Customer customer = getSavedCustomer(request);
        String token = myTokenService.generateAndSaveMyToken(customer);
        int age = validateAge(customer.getDateOfBirth());
        if (age == 0){
            customer.setLocked(true);
            customerRepository.save(customer);
            throw new E_BankException("Cannot register this account because you are less than 16 years old");
        }
        sendVerificationMail(customer, token);
        return RegisterResponse.builder()
                .message("Check your mail for verification token to activate your account")
                .isSuccess(true)
                .build();
    }

    @Override
    public String verifyEmail(EmailVerificationRequest request) {
        Customer registeredCustomer = getCustomerByEmail(request.getEmail());
        if(!registeredCustomer.isLocked()){
            Optional<MyToken> receivedToken = myTokenService.validateReceivedToken(request.getToken(), registeredCustomer);
            registeredCustomer.setEnable(true);
            customerRepository.save(registeredCustomer);
            myTokenService.deleteToken(receivedToken.get());
            return "Verification successful";
        }
        throw new E_BankException("Error verifying email");
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
    @Override
    public void resendVerificationMail(Customer customer) {
        String token = myTokenService.generateAndSaveMyToken(customer);
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
        Customer customer = getCustomerByEmail(request.getEmail());
        if(!customer.getPassword().equals(request.getPassword()))
            throw new InvalidDetailsException("Incorrect Password");
        else return AuthenticationResponse.builder()
                    .message("Authentication successful")
                    .isAuthenticated(true)
                    .build();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                ()-> new NotFoundException("Customer not found"));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("Customer with email %s not found".formatted(email)));
    }

    @Override
    public Customer getCustomerByAccountNumber(String accountNumber) {
        return customerRepository.findByAccount_AccountNumber(accountNumber).orElseThrow(
                ()-> new NotFoundException(String.format("Customer with account number %s not found", accountNumber)));
    }

    @Override
    public String setUpAccount(SetUpAccountRequest request) {
        Customer customer = getCustomerById(request.getUserId());
        Account account = customer.getAccount();
        String accountName = "%s %s"
                .formatted(customer.getFirstName(), customer.getLastName());
        String accountNumber = generateAccountNumber();
        account.setAccountName(accountName);
        account.setAccountNumber(accountNumber);
        account.setPin(request.getPin());
        customerRepository.save(customer);
        return "Account is set up";
    }

    private String generateAccountNumber() {
        SecureRandom randomNumbers = new SecureRandom();
        return randomNumbers.ints(10, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    public String makeDeposit(Long userId, BigDecimal amount) {
        if(amount == null)
            throw new E_BankException("amount cannot be empty");
        Customer customer = getCustomerById(userId);
        Account account = customer.getAccount();
        Transaction transaction = setTransaction(amount, TransactionType.DEPOSIT);
        account.getTransactions().add(transaction);
        customerRepository.save(customer);
        return "Transaction Successful";
    }

    @Override
    public String makeWithdraw(WithDrawRequest request) {
        Customer customer = getCustomerById(request.getUserId());
        Account account = customer.getAccount();
        String pin = account.getPin();
        validatePin(pin, request.getPin());
        BigDecimal balance = calculateBalance(request.getUserId());
        checkWhetherBalanceIsSufficient(balance, request.getAmount());

        Transaction transaction = setTransaction(request.getAmount(), TransactionType.WITHDRAW);
        account.getTransactions().add(transaction);
        customerRepository.save(customer);
        return "Transaction Successful";
    }

    private static void validatePin(String pin, String requestPin) {
        if (!pin.equals(requestPin))
            throw new InvalidDetailsException("Incorrect pin");
    }
    private static void checkWhetherBalanceIsSufficient(BigDecimal balance, BigDecimal requestAmount){
        if(requestAmount.compareTo(balance) > 0)
            throw new E_BankException("Insufficient balance");
    }

    @Override
    public String makeTransfer(TransferRequest request) {
        Customer customer = getCustomerById(request.getUserId());
        Account account = customer.getAccount();
        String pin = account.getPin();
        validatePin(pin, request.getPin());
        BigDecimal balance = calculateBalance(request.getUserId());
        checkWhetherBalanceIsSufficient(balance, request.getAmount());
        return null;
    }

    private static Transaction setTransaction(BigDecimal amount, TransactionType transactionType){
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionTime(LocalDateTime.now());
        return transaction;
    }

    @Override
    public BigDecimal getBalance(Long userId, String pin) {
        Customer customer = getCustomerById(userId);
        String accountPin = customer.getAccount().getPin();
        if(!accountPin.equals(pin))
            throw new InvalidDetailsException("Incorrect pin");
        else return calculateBalance(userId);
    }

    private BigDecimal calculateBalance(Long userId){
        Customer customer = getCustomerById(userId);
        BigDecimal balance = BigDecimal.ZERO;
        List<Transaction> transactions = customer.getAccount().getTransactions();
        for(Transaction transaction : transactions){
            if(transaction.getTransactionType() == TransactionType.DEPOSIT)
                balance = balance.add(transaction.getAmount());
            if(transaction.getTransactionType() == TransactionType.WITHDRAW ||
                    transaction.getTransactionType() == TransactionType.TRANSFER)
                balance = balance.subtract(transaction.getAmount());
        }
        return balance;
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        return null;
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
