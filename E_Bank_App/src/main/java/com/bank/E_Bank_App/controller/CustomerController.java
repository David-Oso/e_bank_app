package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Transaction;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.SetUpAccountResponse;
import com.bank.E_Bank_App.dto.response.UpdateCustomerResponse;
import com.bank.E_Bank_App.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customer/")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(value = "v1/get/{customer_id}", produces = "application/json")
    public ResponseEntity<?> getCustomerById(@Valid @PathVariable Long customer_id) {
        Customer customer = customerService.getCustomerById(customer_id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping(value = "v1/get/email", produces = "application/json")
    public ResponseEntity<?> getCustomerByEmail(@Valid @RequestParam String email){
        Customer customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
    @GetMapping(value = "v1/get/account_number", produces = "application/json")
    public ResponseEntity<?> getCustomerByAccountNumber(@Valid @RequestParam String accountNumber){
        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
    @PostMapping(value = "v1/set_up/account", produces = "application/json")
    public ResponseEntity<?> setUpAccount(@Valid @RequestBody SetUpAccountRequest setUpAccountRequest){
        SetUpAccountResponse setUpAccountResponse = customerService.setUpAccount(setUpAccountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(setUpAccountResponse);
    }
    @PostMapping(value = "v1/deposit", produces = "application/json")
        public ResponseEntity<?> makeDeposit(@Valid @RequestBody DepositRequest depositRequest){
        String depositResponse = customerService.makeDeposit(depositRequest);
        return ResponseEntity.status(HttpStatus.OK).body(depositResponse);
    }
    @PostMapping(value = "v1/withdraw",produces = "application/json")
    public ResponseEntity<?> makeWithdraw(@Valid @RequestBody WithDrawRequest withDrawRequest){
        String withdrawResponse = customerService.makeWithdraw(withDrawRequest);
        return ResponseEntity.status(HttpStatus.OK).body(withdrawResponse);
    }
    @PostMapping(value = "v1/transfer",produces = "application/json")
    public ResponseEntity<?> makeTransfer(@Valid @RequestBody TransferRequest transferRequest){
        String transferResponse = customerService.makeTransfer(transferRequest);
        return ResponseEntity.status(HttpStatus.OK).body(transferResponse);
    }

    @GetMapping(value = "v1/get/balance", produces = "application/json")
    public ResponseEntity<?> getBalance(@Valid @RequestParam Long customerId, @Valid @RequestParam String pin){
        BigDecimal accountBalance = customerService.getBalance(customerId, pin);
        return ResponseEntity.status(HttpStatus.OK).body(accountBalance);
    }

    @PutMapping(value = "v1/update",produces = "application/json")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest){
        UpdateCustomerResponse updateCustomerResponse = customerService.updateCustomer(updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateCustomerResponse);
    }

    @GetMapping(value = "v1/send_reset_password_mail/{customer_id}", produces = "application/json")
    public ResponseEntity<?> sendResetPasswordMail(@Valid @PathVariable Long customer_id){
        String resetPasswordMailResponse = customerService.sendResetPasswordMail(customer_id);
        return ResponseEntity.ok(resetPasswordMailResponse);
    }
    @PostMapping(value = "v1/reset/password", produces = "application/json")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        String resetPasswordResponse = customerService.resetPassword(resetPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(resetPasswordResponse);
    }
    @PostMapping(value = "v1/upload_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public ResponseEntity<?> uploadImage(@Valid @ModelAttribute UploadImageRequest uploadImageRequest){
        String uploadImageResponse = customerService.uploadImage(uploadImageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImageResponse);
    }
    @GetMapping(value = "v1/get/transaction", produces = "application/json")
    public ResponseEntity<?> getTransactionByCustomerIdAndTransactionId(@Valid @RequestParam Long customerId, @Valid @RequestParam Long transactionId){
        Transaction transaction = customerService
                .getTransactionByCustomerIdAndTransactionId(customerId, transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @GetMapping(value = "v1/get/transactions/all/{customer_id}", produces = "application/json")
    public ResponseEntity<?> getAllTransactionsByCustomerId(@Valid @PathVariable Long customer_id){
        List<Transaction> transactions = customerService.getAllTransactionsByCustomerId(customer_id);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
    @DeleteMapping(value = "v1/delete/transaction", produces = "application/json")
    public ResponseEntity<?> deleteTransactionByCustomerIdAndTransactionId(@Valid @RequestParam Long customerId, @Valid @RequestParam Long transactionId){
        String deleteTransactionResponse = customerService
                .deleteTransactionByCustomerIdAndTransactionId(customerId, transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteTransactionResponse);
    }
    @DeleteMapping(value = "v1/delete/transaction/all/{customer_id}", produces = "application/json")
    public ResponseEntity<?> deleteAllTransactionsByCustomerId(@Valid @PathVariable Long customer_id){
        String deleteAllTransactionResponse = customerService
                .deleteAllTransactionsByCustomerId(customer_id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteAllTransactionResponse);
    }

    @DeleteMapping
    public String booth(){
        return "boothe";
    }
}

