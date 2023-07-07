package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Gender;
import com.bank.E_Bank_App.data.model.Transaction;
import com.bank.E_Bank_App.data.model.TransactionType;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import com.bank.E_Bank_App.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.bank.E_Bank_App.utils.E_BankUtils.TEST_IMAGE_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired CustomerService customerService;
    private RegisterRequest registerRequest1;
    private RegisterRequest registerRequest2;
    private EmailVerificationRequest emailVerificationRequest1;
    private EmailVerificationRequest emailVerificationRequest2;
    private AuthenticationRequest authenticationRequest;
    private SetUpAccountRequest setUpAccountRequest1;
    private SetUpAccountRequest setUpAccountRequest2;
    private UploadImageRequest uploadImageRequest;
    private DepositRequest depositRequest;
    private WithDrawRequest withDrawRequest;
    private TransferRequest transferRequest;
    private UpdateCustomerRequest updateCustomerRequest;
    private ResetPasswordRequest resetPasswordRequest;


    @BeforeEach
    void setUp() {
        registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstName("Dave");
        registerRequest1.setLastName("Temz");
        registerRequest1.setEmail("osodavid001@gmail.com");
        registerRequest1.setPassword("Password");
        registerRequest1.setPhoneNumber("+2345098767845");
        registerRequest1.setGender(Gender.MALE);
        registerRequest1.setDateOfBirth("09/08/2003");

        registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstName("Tolu");
        registerRequest2.setLastName("Tope");
        registerRequest2.setEmail("osodavid272@gmail.com");
        registerRequest2.setPassword("Password");
        registerRequest2.setPhoneNumber("+2345098767867");
        registerRequest2.setGender(Gender.FEMALE);
        registerRequest2.setDateOfBirth("10/08/2003");

        emailVerificationRequest1 = new EmailVerificationRequest();
        emailVerificationRequest1.setEmail("osodavid001@gmail.com");
        emailVerificationRequest1.setToken("73-qxlVi");
        emailVerificationRequest2 = new EmailVerificationRequest();
        emailVerificationRequest2.setEmail("osodavid272@gmail.com");
        emailVerificationRequest2.setToken("4vL2Y8dg");

        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("osodavid001@gmail.com");
        authenticationRequest.setPassword("Password");

        setUpAccountRequest1 = new SetUpAccountRequest();
        setUpAccountRequest1.setCustomerId(1L);
        setUpAccountRequest1.setPin("1234");

        setUpAccountRequest2 = new SetUpAccountRequest();
        setUpAccountRequest2.setCustomerId(2L);
        setUpAccountRequest2.setPin("1245");

        depositRequest = new DepositRequest();
        depositRequest.setCustomerId(1L);
        depositRequest.setAmount(BigDecimal.valueOf(50000));

        withDrawRequest = new WithDrawRequest();
        withDrawRequest.setCustomerId(1L);
        withDrawRequest.setAmount(BigDecimal.valueOf(70000));
        withDrawRequest.setPin("1234");

        transferRequest = new TransferRequest();
        transferRequest.setCustomerId(1L);
        transferRequest.setRecipientAccountNumber("9555684342");
        transferRequest.setAmount(BigDecimal.valueOf(20000));
        transferRequest.setPin("1234");

        updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.setUserId(2L);
        updateCustomerRequest.setFirstName("NewFirstName");
        updateCustomerRequest.setLastName("NewLastName");
        updateCustomerRequest.setPassword("NewPassword");
        updateCustomerRequest.setGender(Gender.MALE);
        updateCustomerRequest.setNewPassword("NewPassword");
        updateCustomerRequest.setDateOfBirth("21/08/2004");

        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("osodavid272@gmail.com");
        resetPasswordRequest.setToken("8B-doU-w");
        resetPasswordRequest.setNewPassword("NewPassword");
        resetPasswordRequest.setConfirmPassword("NewPassword");

        uploadImageRequest = new UploadImageRequest();
        uploadImageRequest.setCustomerId(1L);
        uploadImageRequest.setProfileImage(uploadTestImageFile(TEST_IMAGE_LOCATION));
    }

    private MultipartFile uploadTestImageFile(String imageUrl){
        MultipartFile file = null;
        try{
            file =  new MockMultipartFile("test_upload_image",
                    new FileInputStream(imageUrl));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        return file;
    }

    @Test
    void registerUserTest() {
        RegisterResponse response = customerService.register(registerRequest1);
        assertThat(response.getMessage()).isEqualTo("Check your mail for verification token to activate your account");
        assertThat(response.isSuccess()).isEqualTo(true);

        RegisterResponse response1 = customerService.register(registerRequest2);
        assertThat(response1.getMessage()).isEqualTo("Check your mail for verification token to activate your account");
        assertThat(response1.isSuccess()).isEqualTo(true);
    }

    @Test
    void resendVerificationMailTest() {
//        customerService.resendVerificationMail();
    }

    @Test
    void verifyEmailTest(){
        String response1 = customerService.verifyEmail(emailVerificationRequest1);
        assertThat(response1).isEqualTo("Verification successful");

        String response2 = customerService.verifyEmail(emailVerificationRequest2);
        assertThat(response2).isEqualTo("Verification successful");
    }

    @Test
    void authenticateUserTest() {
        AuthenticationResponse response = customerService.authenticate(authenticationRequest);
        assertThat(response.getMessage()).isEqualTo("Authentication successful");
        assertThat(response.isAuthenticated()).isEqualTo(true);
    }

    @Test
    void getCustomerById() {
        Customer foundCustomer = customerService.getCustomerById(1L);
        assertThat(foundCustomer.getEmail()).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void getCustomerByEmail() {
        Customer foundCustomer = customerService.getCustomerByEmail("osodavid001@gmail.com");
        assertThat(foundCustomer.getFirstName()).isEqualTo("Dave");
        assertThat(foundCustomer.getLastName()).isEqualTo("Temz");
    }

    @Test
    void getCustomerByAccountNumberTest(){
        Customer foundCustomer = customerService.getCustomerByAccountNumber("9872958385");
        assertThat(foundCustomer.getEmail()).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void setUpAccountTest(){
        String response1 = customerService.setUpAccount(setUpAccountRequest1);
        assertThat(response1).isEqualTo("Account is set up");
        String response2 = customerService.setUpAccount(setUpAccountRequest2);
        assertThat(response2).isEqualTo("Account is set up");
    }

    @Test
    void makeDepositTest(){
        String response = customerService.makeDeposit(depositRequest);
        assertThat(response).isEqualTo("Transaction Successful");
    }

    @Test
    void getBalanceTest(){
        BigDecimal balance = customerService.getBalance(1L, "1234");
        assertThat(balance).isEqualTo(BigDecimal.valueOf(50000).setScale(2));
    }

    @Test
    void makeWithdrawTest(){
        String response = customerService.makeWithdraw(withDrawRequest);
        assertThat(response).isEqualTo("Transaction Successful");
//        BigDecimal balance = customerService.getBalance(withDrawRequest.getUserId(), withDrawRequest.getPin());
//        assertThat(balance).isEqualTo(BigDecimal.valueOf(40000).setScale(2));
    }

    @Test
    void makeTransferTest(){
        String response  = customerService.makeTransfer(transferRequest);
        assertThat(response).isEqualTo("Transaction Successful");
//        BigDecimal balance = customerService.getBalance(transferRequest.getUserId(), transferRequest.getPin());
//        assertThat(balance).isEqualTo(BigDecimal.valueOf(20000).setScale(2));

//        Customer recipient = customerService.getCustomerByAccountNumber(transferRequest.getRecipientAccountNumber());
//        String recipientPin = recipient.getAccount().getPin();
//        BigDecimal recipientBalance = customerService.getBalance(recipient.getId(), recipientPin);
//        assertThat(recipientBalance).isEqualTo(BigDecimal.valueOf(20000).setScale(2));
    }

    @Test
    void updateCustomerTest(){
        String response = customerService.updateCustomer(updateCustomerRequest);
        assertThat(response).isEqualTo("Customer Updated Successfully");
    }

    @Test
    void sendResetPasswordMailTest(){
        String response = customerService.sendRequestPasswordMail(2L);
        assertThat(response).isEqualTo("Check your email to reset your password");
    }

    @Test
    void resetPasswordTest(){
        String response = customerService.resetPassword(resetPasswordRequest);
        assertThat(response).isEqualTo("Password reset successful");
    }

    @Test
    void uploadImageTest(){
        String response = customerService.uploadImage(uploadImageRequest);
        assertThat(response).isEqualTo("Profile image uploaded");
    }

    @Test
    void getTransactionByIdTest(){
        Transaction transaction = customerService.getTransactionById(1L, 1L);
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(50000.00));
        assertThat(transaction.getTransactionType()).isEqualTo(TransactionType.DEPOSIT);
//        assertThat(transaction.getTransactionTime()).isEqualTo(LocalDateTime.parse("2023-07-05 15:58:22.708181"));
    }

    @Test
    void getAllTransactionsTest(){
        List<Transaction> transactions = customerService.getAllTransactions(1L);
        assertThat(transactions).isNotNull();
    }
}
