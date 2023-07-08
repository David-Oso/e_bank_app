package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Transaction;
import com.bank.E_Bank_App.dto.request.*;
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
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("get/{customer_id}")
    public ResponseEntity<?> getCustomerById(@Valid @PathVariable Long customer_id) {
        Customer customer = customerService.getCustomerById(customer_id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping("get/email")
    public ResponseEntity<?> getCustomerByEmail(@Valid @RequestParam String email){
        Customer customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
    @GetMapping("get/account_number")
    public ResponseEntity<?> getCustomerByAccountNumber(@Valid @RequestParam String accountNumber){
        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
    @PostMapping("set_up/account")
    public ResponseEntity<?> setUpAccount(@Valid @RequestBody SetUpAccountRequest setUpAccountRequest){
        String setUpAccountResponse = customerService.setUpAccount(setUpAccountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(setUpAccountResponse);
    }
    @PostMapping("deposit")
        public ResponseEntity<?> makeDeposit(@Valid @RequestBody DepositRequest depositRequest){
        String depositResponse = customerService.makeDeposit(depositRequest);
        return ResponseEntity.status(HttpStatus.OK).body(depositResponse);
    }
    @PostMapping("withdraw")
    public ResponseEntity<?> makeWithdraw(@Valid @RequestBody WithDrawRequest withDrawRequest){
        String withdrawResponse = customerService.makeWithdraw(withDrawRequest);
        return ResponseEntity.status(HttpStatus.OK).body(withdrawResponse);
    }
    @PostMapping("transfer")
    public ResponseEntity<?> makeTransfer(@Valid @RequestBody TransferRequest transferRequest){
        String transferResponse = customerService.makeTransfer(transferRequest);
        return ResponseEntity.status(HttpStatus.OK).body(transferResponse);
    }

    @GetMapping("get/balance")
    public ResponseEntity<?> getBalance(@Valid @RequestParam Long customerId, @Valid @RequestParam String pin){
        BigDecimal accountBalance = customerService.getBalance(customerId, pin);
        return ResponseEntity.status(HttpStatus.OK).body(accountBalance);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest){
        String updateCustomerResponse = customerService.updateCustomer(updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateCustomerResponse);
    }
    @PostMapping("reset/password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        String resetPasswordResponse = customerService.resetPassword(resetPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(resetPasswordResponse);
    }
    @PostMapping(value = "upload_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@Valid @ModelAttribute UploadImageRequest uploadImageRequest){
        String uploadImageResponse = customerService.uploadImage(uploadImageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImageResponse);
    }
    @GetMapping("get/transaction")
    public ResponseEntity<?> getTransactionByCustomerIdAndTransactionId(@Valid @RequestParam Long customerId, @Valid @RequestParam Long transactionId){
        Transaction transaction = customerService
                .getTransactionByCustomerIdAndTransactionId(customerId, transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @GetMapping("get/transactions/all/{customer_id}")
    public ResponseEntity<?> getAllTransactionsByCustomerId(@Valid @PathVariable Long customer_id){
        List<Transaction> transactions = customerService.getAllTransactionsByCustomerId(customer_id);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
    @DeleteMapping("delete/transaction")
    public ResponseEntity<?> deleteTransactionByCustomerIdAndTransactionId(@Valid @RequestParam Long customerId, @Valid @RequestParam Long transactionId){
        String deleteTransactionResponse = customerService
                .deleteTransactionByCustomerIdAndTransactionId(customerId, transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteTransactionResponse);
    }
    @DeleteMapping("delete/transaction/all/{customer_id}")
    public ResponseEntity<?> deleteAllTransactionsByCustomerId(@Valid @PathVariable Long customer_id){
        String deleteAllTransactionResponse = customerService
                .deleteAllTransactionsByCustomerId(customer_id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteAllTransactionResponse);
    }
}

