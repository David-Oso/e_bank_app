package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.data.model.*;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.*;
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
import java.util.List;

import static com.bank.E_Bank_App.utils.E_BankUtils.TEST_IMAGE_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired CustomerService customerService;
    private RegisterRequest registerRequest1;
    private RegisterRequest registerRequest2;
    private LoginRequest loginRequest;
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

        loginRequest = new LoginRequest();
        loginRequest.setEmail("osodavid001@gmail.com");
        loginRequest.setPassword("Password");

        setUpAccountRequest1 = new SetUpAccountRequest();
        setUpAccountRequest1.setCustomerId(1L);
        setUpAccountRequest1.setPin("1234");

        setUpAccountRequest2 = new SetUpAccountRequest();
        setUpAccountRequest2.setCustomerId(2L);
        setUpAccountRequest2.setPin("1245");

        depositRequest = new DepositRequest();
        depositRequest.setCustomerId(1L);
        depositRequest.setAmount(BigDecimal.valueOf(100000));

        withDrawRequest = new WithDrawRequest();
        withDrawRequest.setCustomerId(1L);
        withDrawRequest.setAmount(BigDecimal.valueOf(20000));
        withDrawRequest.setPin("1234");

        transferRequest = new TransferRequest();
        transferRequest.setCustomerId(1L);
        transferRequest.setRecipientAccountNumber("5223018037");
        transferRequest.setAmount(BigDecimal.valueOf(20000));
        transferRequest.setPin("1234");

        updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.setUserId(2L);
        updateCustomerRequest.setFirstName("NewFirstName");
        updateCustomerRequest.setLastName("NewLastName");
        updateCustomerRequest.setGender(Gender.MALE);
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
        assertThat(response.getMessage()).isEqualTo("Check your mail for otp to activate your account");
        assertThat(response.isSuccess()).isEqualTo(true);

        RegisterResponse response1 = customerService.register(registerRequest2);
        assertThat(response1.getMessage()).isEqualTo("Check your mail for otp to activate your account");
        assertThat(response1.isSuccess()).isEqualTo(true);
    }

    @Test
    void resendVerificationMailTest(){
        String response = customerService.resendVerificationMail(2L);
        assertThat(response).isEqualTo("Another otp has sent to your mail proceed by checking your email");
    }

    @Test
    void verifyEmailTest(){
        OtpVerificationResponse response1 = customerService.verifyEmail("539256");
        assertThat(response1.getEmail()).isEqualTo(registerRequest1.getEmail());

        OtpVerificationResponse response2 = customerService.verifyEmail("732822");
        assertThat(response2.getEmail()).isEqualTo(registerRequest2.getEmail());
    }

    @Test
    void authenticateUserTest() {
        LoginResponse response = customerService.login(loginRequest);
        assertThat(response.getJwtResponse()).isNotNull();
    }

    @Test
    void getCustomerById() {
        Customer foundCustomer = customerService.getCustomerById(1L);
        String email = foundCustomer.getAppUser().getEmail();
        assertThat(email).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void getCustomerByEmail() {
        Customer foundCustomer = customerService.getCustomerByEmail("osodavid001@gmail.com");
        String firstName = foundCustomer.getAppUser().getFirstName();
        String lastName = foundCustomer.getAppUser().getLastName();
        assertThat(firstName).isEqualTo(registerRequest1.getFirstName());
        assertThat(lastName).isEqualTo(registerRequest1.getLastName());
    }

    @Test
    void setUpAccountTest(){
        SetUpAccountResponse response1 = customerService.setUpAccount(setUpAccountRequest1);
        assertThat(response1.getMessage()).isEqualTo("Account is set up");
        assertThat(response1.getAccountNumber()).isNotNull();
        SetUpAccountResponse response2 = customerService.setUpAccount(setUpAccountRequest2);
        assertThat(response2.getMessage()).isEqualTo("Account set up successful");
        assertThat(response2.getAccountNumber()).isNotNull();
    }

    @Test
    void getCustomerByAccountNumberTest(){
        Customer foundCustomer = customerService.getCustomerByAccountNumber("0284272228");
        String email = foundCustomer.getAppUser().getEmail();
        assertThat(email).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void makeDepositTest(){
        String response = customerService.makeDeposit(depositRequest);
        assertThat(response).isEqualTo("Transaction Successful");
    }

    @Test
    void getBalanceTest(){
        BigDecimal balance = customerService.getBalance(1L, "1234");
        assertThat(balance).isEqualTo(BigDecimal.valueOf(100000).setScale(2));
    }

    @Test
    void makeWithdrawTest(){
        String response = customerService.makeWithdraw(withDrawRequest);
        assertThat(response).isEqualTo("Transaction Successful");
        BigDecimal balance = customerService.getBalance(withDrawRequest.getCustomerId(), withDrawRequest.getPin());
        assertThat(balance).isEqualTo(BigDecimal.valueOf(80000).setScale(2));
    }

    @Test
    void makeTransferTest(){
        String response  = customerService.makeTransfer(transferRequest);
        assertThat(response).isEqualTo("Transaction Successful");
        BigDecimal balance = customerService.getBalance(transferRequest.getCustomerId(), transferRequest.getPin());
        assertThat(balance).isEqualTo(BigDecimal.valueOf(60000).setScale(2));

        Customer recipient = customerService.getCustomerByAccountNumber(transferRequest.getRecipientAccountNumber());
        String recipientPin = recipient.getAccount().getPin();
        BigDecimal recipientBalance = customerService.getBalance(recipient.getId(), recipientPin);
        assertThat(recipientBalance).isEqualTo(BigDecimal.valueOf(20000).setScale(2));
    }

    @Test
    void updateCustomerTest(){
        UpdateCustomerResponse response = customerService.updateCustomer(updateCustomerRequest);
        assertThat(response).isNotNull();
    }

    @Test
    void updatePasswordTest(){
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUserId(2L);
        changePasswordRequest.setPassword("Password");
        changePasswordRequest.setNewPassword("NewPassword");
        String changePasswordResponse = customerService.changePassword(changePasswordRequest);
        assertThat(changePasswordResponse).isEqualTo("Customer password updated successfully");
    }

    @Test
    void sendResetPasswordMailTest(){
        String response = customerService.sendResetPasswordMail(2L);
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
    void getTransactionByCustomerIdAndTransactionIdTest(){
        Transaction transaction = customerService.getTransactionByCustomerIdAndTransactionId(1L, 1L);
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(50000.00));
        assertThat(transaction.getTransactionType()).isEqualTo(TransactionType.DEPOSIT);
//        assertThat(transaction.getTransactionTime()).isEqualTo(LocalDateTime.parse("2023-07-05 15:58:22.708181"));
    }

    @Test
    void getAllTransactionsByCustomerIdTest(){
        List<Transaction> transactions = customerService.getAllTransactionsByCustomerId(1L);
        assertThat(transactions).isNotNull();
    }

    @Test
    void deleteTransactionByCustomerIdAndTransactionIdTest(){
        String response = customerService.deleteTransactionByCustomerIdAndTransactionId(2L, 1L);
        assertThat(response).isEqualTo("Transaction deleted");
    }
    @Test
    void deleteAllTransactionByCustomerIdTest(){
        String response = customerService.deleteAllTransactionsByCustomerId(2L);
        assertThat(response).isEqualTo("Transactions deleted successfully");
    }
}
