package com.bank.E_Bank_App.service.customer;

import com.bank.E_Bank_App.data.model.*;
import com.bank.E_Bank_App.data.repository.CustomerRepository;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import com.bank.E_Bank_App.exception.E_BankException;
import com.bank.E_Bank_App.exception.InvalidDetailsException;
import com.bank.E_Bank_App.exception.NotFoundException;
import com.bank.E_Bank_App.service.mail.MailService;
import com.bank.E_Bank_App.service.myToken.MyTokenService;
import com.bank.E_Bank_App.service.cloud.CloudService;
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
    private final CloudService cloudService;
//    private final PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        checkIfEmailAlreadyExists(request.getEmail());
        Customer customer = getSavedCustomer(request);
        String token = myTokenService.generateAndSaveMyToken(customer);
        int age = changeDateToIntAndValidateAge(customer.getDateOfBirth());
//        customer.setAge(age);
        customerRepository.save(customer);
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
        Customer customer = getCustomerById(request.getCustomerId());
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
    public String makeDeposit(DepositRequest request) {

        Customer customer = getCustomerById(request.getCustomerId());
        Account account = customer.getAccount();
        Transaction transaction = setTransaction(request.getAmount(), TransactionType.DEPOSIT);
        account.getTransactions().add(transaction);
        customerRepository.save(customer);
        sendDepositNotification(customer, request.getAmount(),false, null);
        return "Transaction Successful";
    }

    private void sendDepositNotification(Customer customer, BigDecimal amount, boolean isTransfer, Customer fromCustomer) {
        String mailTemplate = E_BankUtils.GET_DEPOSIT_NOTIFICATION_MAIL_TEMPLATE;
        String name = customer.getFirstName();
        String accountName = "%s %s".formatted(customer.getFirstName(), customer.getLastName());
        StringBuilder number = new StringBuilder(customer.getAccount().getAccountNumber());
        String accountNumber = number.replace(2, 8, "********").toString();
        String transactionType = "Deposit";
        String description = "";
        if(isTransfer)
            description = "Transfer from %s %s".formatted(fromCustomer.getFirstName(), fromCustomer.getLastName());
        else description = "Deposit into your account";
        String transactionAmount = "₦%s".formatted(amount);
        String transactionDateAndTime = DateTimeFormatter.ofPattern("EEE, dd/MM/yy, hh:mm:ss a").format(LocalDateTime.now());
        String currentBalance = "₦%s".formatted(calculateBalance(customer.getId()));
        String myPhoneNumber = E_BankUtils.BANK_PHONE_NUMBER;
        String myEmail = "osodavid001@gmail.com";
        String subject = "Credit Alert Notification";
        String htmlContent = String.format(mailTemplate, name, accountName, accountNumber, transactionType,
                description, transactionAmount, transactionDateAndTime, currentBalance, myPhoneNumber, myEmail);
        mailService.sendHtmlMail(name, customer.getEmail(), subject, htmlContent);
    }

    @Override
    public String makeWithdraw(WithDrawRequest request) {
        Customer customer = getCustomerById(request.getCustomerId());
        Account account = customer.getAccount();
        String pin = account.getPin();
        validatePin(pin, request.getPin());
        BigDecimal balance = calculateBalance(request.getCustomerId());
        checkWhetherBalanceIsSufficient(balance, request.getAmount());
        Transaction transaction = setTransaction(request.getAmount(), TransactionType.WITHDRAW);
        account.getTransactions().add(transaction);
        customerRepository.save(customer);
        sendWithdrawNotificationMail(customer, request.getAmount());
        return "Transaction Successful";
    }

    private void sendWithdrawNotificationMail(Customer customer, BigDecimal amount) {
        String mailTemplate = E_BankUtils.GET_WITHDRAW_NOTIFICATION_MAIL_TEMPLATE;
        String name = customer.getFirstName();
        String accountName = "%s %s".formatted(customer.getFirstName(), customer.getLastName());
        StringBuilder number = new StringBuilder(customer.getAccount().getAccountNumber());
        String accountNumber = number.replace(2, 8, "********").toString();
        String transactionType = "Withdraw";
        String description = "Withdrawal from bank";
        String transactionAmount = "₦%s".formatted(amount);
        String transactionDateAndTime = DateTimeFormatter.ofPattern("EEE, dd/MM/yy, hh:mm:ss a").format(LocalDateTime.now());
        String currentBalance = "₦%s".formatted(calculateBalance(customer.getId()));
        String myPhoneNumber = E_BankUtils.BANK_PHONE_NUMBER;
        String myEmail = "osodavid001@gmail.com";
        String subject = "Debit Alert Notification";
        String htmlContent = String.format(mailTemplate, name, accountName, accountNumber, transactionType,
                description, transactionAmount, transactionDateAndTime, currentBalance, myPhoneNumber, myEmail);
        mailService.sendHtmlMail(name, customer.getEmail(), subject, htmlContent);
    }

    private static void validatePin(String pin, String requestPin) {
        if (pin == null)
            throw new E_BankException("Input your pin");
        else if(!pin.equals(requestPin))
            throw new InvalidDetailsException("Incorrect pin");
    }
    private static void checkWhetherBalanceIsSufficient(BigDecimal balance, BigDecimal requestAmount){
        if(requestAmount.compareTo(balance) > 0)
            throw new E_BankException("Insufficient balance");
    }

    @Override
    public String makeTransfer(TransferRequest request) {
        Customer customer = getCustomerById(request.getCustomerId());
        Account account = customer.getAccount();
        String pin = account.getPin();
        validatePin(pin, request.getPin());
        BigDecimal balance = calculateBalance(request.getCustomerId());
        checkWhetherBalanceIsSufficient(balance, request.getAmount());
        Customer recipient = getCustomerByAccountNumber(request.getRecipientAccountNumber());
        Account recipientAccount = recipient.getAccount();

        Transaction transaction = setTransaction(request.getAmount(), TransactionType.TRANSFER);
        account.getTransactions().add(transaction);
        customerRepository.save(customer);

        transaction.setTransactionType(TransactionType.DEPOSIT);
        recipientAccount.getTransactions().add(transaction);
        customerRepository.save(recipient);
        sendTransferNotificationMail(customer, request.getAmount(), recipientAccount.getAccountNumber());
        sendDepositNotification(recipient, request.getAmount(), true, recipient);
        return "Transaction Successful";
    }

    private void sendTransferNotificationMail(Customer customer, BigDecimal amount, String toAccountNumber) {
        String mailTemplate = E_BankUtils.GET_TRANSFER_NOTIFICATION_MAIL_TEMPLATE;
        String name = customer.getFirstName();
        String accountName = "%s %s".formatted(customer.getFirstName(), customer.getLastName());
        StringBuilder number = new StringBuilder(customer.getAccount().getAccountNumber());
        String accountNumber = number.replace(2, 8, "********").toString();
        String recipientAccountNumber = new StringBuilder(toAccountNumber).replace(2, 8, "********").toString();
        String transactionType = "Transfer";
        String description = "Transfer to account number %s".formatted(toAccountNumber);
        String transactionAmount = "₦%s".formatted(amount);
        String transactionDateAndTime = DateTimeFormatter.ofPattern("EEE, dd/MM/yy, hh:mm:ss a").format(LocalDateTime.now());
        String currentBalance = "₦%s".formatted(calculateBalance(customer.getId()));
        String myPhoneNumber = E_BankUtils.BANK_PHONE_NUMBER;
        String myEmail = "osodavid001@gmail.com";
        String subject = "Transfer Transaction Notification";
        String htmlContent = String.format(mailTemplate, name, accountName, accountNumber, recipientAccountNumber, description,
                transactionType, transactionAmount, transactionDateAndTime, currentBalance, myPhoneNumber, myEmail);
        mailService.sendHtmlMail(name, customer.getEmail(), subject, htmlContent);
    }

    private static Transaction setTransaction(BigDecimal amount, TransactionType transactionType){
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionTime(LocalDateTime.now());
        return transaction;
    }

    @Override
    public BigDecimal getBalance(Long customerId, String pin) {
        if (customerId != null){
            Customer customer = getCustomerById(customerId);
            String accountPin = customer.getAccount().getPin();
            if(!accountPin.equals(pin))
                throw new InvalidDetailsException("Incorrect pin");
            else return calculateBalance(customerId);
        }
        throw new E_BankException("field customer id cannot be null");
    }

    @Override
    public String updateCustomer(UpdateCustomerRequest request) {
        Customer customer = getCustomerById(request.getUserId());
        if(!customer.getPassword().equals(request.getPassword()))
            throw new InvalidDetailsException("Incorrect password");
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setGender(request.getGender());
        LocalDate dateOfBirth = convertDateOBirthToLocalDate(request.getDateOfBirth());
        int age = changeDateToIntAndValidateAge(dateOfBirth);
        customer.setDateOfBirth(dateOfBirth);
//        customer.setAge(age);
        customer.setPassword(request.getNewPassword());
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        return "Customer Updated Successfully";
    }

    private BigDecimal calculateBalance(Long customerId){
        Customer customer = getCustomerById(customerId);
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
    public String sendRequestPasswordMail(Long customerId) {
        Customer customer = getCustomerById(customerId);
        String mailTemplate = E_BankUtils.GET_RESET_PASSWORD_MAIL_TEMPLATE;
        String firstName = customer.getFirstName();
        String token = myTokenService.generateAndSaveMyToken(customer);
        String htmlContent = String.format(mailTemplate, firstName, token, E_BankUtils.BANK_PHONE_NUMBER);
        String subject = "Reset Password";
        mailService.sendHtmlMail(firstName, customer.getEmail(), subject, htmlContent);
        return "Check your email to reset your password";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        Customer customer = getCustomerByEmail(request.getEmail());
        Optional<MyToken> receivedToken = myTokenService.validateReceivedToken(request.getToken(), customer);
        customer.setPassword(request.getNewPassword());
        if(!customer.getPassword().equals(request.getConfirmPassword()))
            throw new InvalidDetailsException("Password doesn't match");
        customerRepository.save(customer);
        myTokenService.deleteToken(receivedToken.get());
        return "Password reset successful";
    }

    @Override
    public String uploadImage(UploadImageRequest request) {
        Customer customer= getCustomerById(request.getCustomerId());
        String imageUrl = cloudService.upload(request.getProfileImage());
//        customer.setImageUrl(imageUrl);
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        return "Profile image uploaded";
    }

    @Override
    public Transaction getTransactionById(Long customerId, Long transactionId) {
        Customer customer = getCustomerById(customerId);
        List<Transaction> transactions = customer.getAccount().getTransactions();
        for(Transaction transaction : transactions){
            if (transaction.getId().equals(transactionId))
                return transaction;
        }
        throw new E_BankException("Transaction not found");
    }

    @Override
    public List<Transaction> getAllTransaction(Long customerId) {
        Customer customer = getCustomerById(customerId);
        return customer.getAccount().getTransactions();
    }

    @Override
    public void deleteTransaction(Long customerId, Long transactionId) {
        if(customerId == null)
            throw new E_BankException("Customer id cannot be null");
        if(transactionId == null)
            throw new E_BankException("Transaction id cannot be null");
    }

    @Override
    public void deleteAllTransactions(Long customerId) {

    }

    private LocalDate convertDateOBirthToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
    private int changeDateToIntAndValidateAge(LocalDate date) {
        int years = Period.between(date, LocalDate.now()).getYears();
        if(years < 16)
            throw new E_BankException("Cannot register this account because you are less than 16 years old");
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
