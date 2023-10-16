package com.bank.E_Bank_App.service.customer;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Gender;
import com.bank.E_Bank_App.data.model.Transaction;
import com.bank.E_Bank_App.data.model.TransactionType;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.bank.E_Bank_App.utils.E_BankUtils.TEST_IMAGE_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceImplTest {
    @Autowired CustomerService customerService;
    RegisterRequest registerRequest1;
    RegisterRequest registerRequest2;

    @BeforeEach
    void setUp() {
        registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstName("Dave");
        registerRequest1.setLastName("Temz");
        registerRequest1.setEmail("pehey92995@mugadget.com");
        registerRequest1.setPassword("Password");
        registerRequest1.setPhoneNumber("+2345098767849");
        registerRequest1.setGender(Gender.MALE);
        registerRequest1.setDateOfBirth("09/08/2003");

        registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstName("Tolu");
        registerRequest2.setLastName("Tope");
        registerRequest2.setEmail("jepoxeg565@ksyhtc.com");
        registerRequest2.setPassword("Password");
        registerRequest2.setPhoneNumber("+2345098767867");
        registerRequest2.setGender(Gender.FEMALE);
        registerRequest2.setDateOfBirth("10/08/2003");
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
    void register() {
        RegisterResponse response = customerService.register(registerRequest1);
        assertThat(response.getMessage()).isEqualTo("Check your mail for otp to activate your account");
        assertThat(response.isSuccess()).isEqualTo(true);

        RegisterResponse response1 = customerService.register(registerRequest2);
        assertThat(response1.getMessage()).isEqualTo("Check your mail for otp to activate your account");
        assertThat(response1.isSuccess()).isEqualTo(true);
    }

    @Test
    void verifyEmail() {
        OtpVerificationResponse response1 = customerService.verifyEmail("172262");
        assertThat(response1.getEmail()).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void resendVerificationMail() {
        String response = customerService.resendVerificationMail(registerRequest1.getEmail());
        assertThat(response).isEqualTo("Another otp has sent to your mail proceed by checking your email");
    }

    @Test
    void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("pehey92995@mugadget.com");
        loginRequest.setPassword("Password");

        LoginResponse loginResponse = customerService.login(loginRequest);
        assertThat(loginResponse.getJwtResponse()).isNotNull();
    }

    @Test
    void getCustomerById() {
        Customer customer = customerService.getCustomerById(3L);
        String email = customer.getAppUser().getEmail();
        assertThat(email).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void getCustomerByEmail() {
        Customer customer = customerService.getCustomerByEmail("pehey92995@mugadget.com");
        String firstName = customer.getAppUser().getFirstName();
        String lastName = customer.getAppUser().getLastName();
        assertThat(firstName).isEqualTo(registerRequest1.getFirstName());
        assertThat(lastName).isEqualTo(registerRequest1.getLastName());
    }

    @Test
    void setUpAccount() {
        SetUpAccountRequest accountRequest = new SetUpAccountRequest();
        accountRequest.setCustomerId(3L);
        accountRequest.setPin("2345");
//        3199499391
//        4377816961

        SetUpAccountResponse response = customerService.setUpAccount(accountRequest);
        assertThat(response.getMessage()).isEqualTo("Account set up successful");
        assertThat(response.getAccountNumber()).isNotNull();
        assertThat(response.getAccountName()).isNotNull();
    }

    @Test
    void getCustomerByAccountNumber() {
        Customer customer = customerService.getCustomerByAccountNumber("3199499391");
        String email = customer.getAppUser().getEmail();
        assertThat(email).isEqualTo(registerRequest2.getEmail());
    }

    @Test
    void makeDeposit() {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setCustomerId(3L);
        depositRequest.setAmount(BigDecimal.valueOf(10000.00));
        depositRequest.setDescription("Making deposit to my account");

        String response = customerService.makeDeposit(depositRequest);
        assertThat(response).isEqualTo("Transaction Successful");
    }

    @Test
    void makeWithdraw() {
        WithDrawRequest withDrawRequest = new WithDrawRequest();
        withDrawRequest.setCustomerId(3L);
        withDrawRequest.setPin("2345");
        withDrawRequest.setAmount(BigDecimal.valueOf(2000.00));
        withDrawRequest.setDescription("Making withdraw");

        String response = customerService.makeWithdraw(withDrawRequest);
        assertThat(response).isEqualTo("Transaction Successful");
        BigDecimal balance = customerService.getBalance(withDrawRequest.getCustomerId(), withDrawRequest.getPin());
        assertThat(balance).isEqualTo(BigDecimal.valueOf(8000).setScale(2));
    }

    @Test
    void makeTransferTest(){
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAmount(BigDecimal.valueOf(2000.00));
        transferRequest.setCustomerId(3L);
        transferRequest.setPin("2345");
        transferRequest.setRecipientAccountNumber("3199499391");
        transferRequest.setDescription("Making Transfer");

        String response  = customerService.makeTransfer(transferRequest);
        assertThat(response).isEqualTo("Transaction Successful");
        BigDecimal balance = customerService.getBalance(transferRequest.getCustomerId(), transferRequest.getPin());
        assertThat(balance).isEqualTo(BigDecimal.valueOf(6000).setScale(2));

        Customer recipient = customerService.getCustomerByAccountNumber(transferRequest.getRecipientAccountNumber());
        String recipientPin = recipient.getAccount().getPin();
        BigDecimal recipientBalance = customerService.getBalance(recipient.getId(), recipientPin);
        assertThat(recipientBalance).isEqualTo(BigDecimal.valueOf(2000).setScale(2));
    }

    @Test
    void getBalanceTest(){
        BigDecimal balance = customerService.getBalance(3L, "2345");
        assertThat(balance).isEqualTo(BigDecimal.valueOf(6000).setScale(2));
    }

    @Test
    void  updateCustomerTest(){
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setUserId(2L);
        request.setFirstName("Yemi");
        request.setLastName("Sola");

        UpdateCustomerResponse response = customerService.updateCustomer(request);
        assertThat(response.getFirstName()).isEqualTo("Yemi");
        assertThat(response.getLastName()).isEqualTo("Sola");
    }

    @Test
    void changePasswordTest(){
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setUserId(2L);
        request.setPassword("Password");
        request.setNewPassword("NewPassword");

        String response = customerService.changePassword(request);
        assertThat(response).isEqualTo("Customer password updated successfully");
    }

    @Test
    void sendResetPasswordMailTest(){
        String response = customerService.sendResetPasswordMail(registerRequest2.getEmail());
        assertThat(response).isEqualTo("Check your email to reset your password");
    }

//    636448
    @Test
    void resetPasswordTest(){
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setOtp("636448");
        request.setNewPassword("Password1.");
        request.setConfirmPassword("Password1.");

        String response = customerService.resetPassword(request);
        assertThat(response).isEqualTo("Password reset successful");
    }

    @Test
    void uploadImageTest(){
        UploadImageRequest imageRequest = new UploadImageRequest();
        imageRequest.setCustomerId(2L);
        imageRequest.setProfileImage(uploadTestImageFile(TEST_IMAGE_LOCATION));

        String response = customerService.uploadImage(imageRequest);
        assertThat(response).isEqualTo("Profile image uploaded");
    }

    @Test
    void getTransactionByCustomerIdAndTransactionIdTest(){
        Transaction transaction = customerService.getTransactionByCustomerIdAndTransactionId(2L, 4L);
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(2000).setScale(2));
        assertThat(transaction.getTransactionType()).isEqualTo(TransactionType.CREDIT);
    }

    @Test
    void getAllTransactionsTest(){
        List<Transaction> transactions = customerService.getAllTransactionsByCustomerId(2L);
        assertThat(transactions.size()).isEqualTo(1);

        List<Transaction> transactions1 = customerService.getAllTransactionsByCustomerId(3L);
        assertThat(transactions1.size()).isEqualTo(3);
    }

    @Test
    void deleteTransactionByCustomerIdAndTransactionIdTest(){
        String response = customerService.deleteTransactionByCustomerIdAndTransactionId(3L, 1L);
        assertThat(response).isEqualTo("Transaction deleted");

        List<Transaction> transactions = customerService.getAllTransactionsByCustomerId(3L);
        assertThat(transactions.size()).isEqualTo(2);
    }
}