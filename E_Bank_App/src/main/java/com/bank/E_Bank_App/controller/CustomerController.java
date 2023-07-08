package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("get/{customer_id}")
    public ResponseEntity<?> getCustomerById(@Valid @PathVariable Long customer_id){
        return null;
    }

    @GetMapping("get/email")
    public ResponseEntity<?> getCustomerByEmail(@Valid @RequestParam String email){
        return null;
    }
    @GetMapping("get/account_number")
    public ResponseEntity<?> getCustomerByAccountNumber(@Valid @RequestParam String accountNumber){
        return null;
    }
    @PostMapping("set_up/account")
    public ResponseEntity<?> setUpAccount(@Valid @RequestBody SetUpAccountRequest setUpAccountRequest){
        return null;
    }
    @PostMapping("deposit")
        public ResponseEntity<?> makeDeposit(@Valid @RequestBody DepositRequest depositRequest){
        return null;
    }
    @PostMapping("withdraw")
    public ResponseEntity<?> makeWithdraw(@Valid @RequestBody WithDrawRequest withDrawRequest){
        return null;
    }
    @PostMapping("transfer")
    public ResponseEntity<?> makeTransfer(@Valid @RequestBody TransferRequest transferRequest){
        return null;
    }

    @GetMapping("get/balance")
    public ResponseEntity<?> getBalance(@Valid @RequestParam Long customerId, @Valid @RequestParam String pin){
        return null;
    }

    @PutMapping("update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest){
        return null;
    }
    @PostMapping("reset/password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        return null;
    }
    @PostMapping(value = "upload_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@Valid @ModelAttribute UploadImageRequest uploadImageRequest){
        String response = customerService.uploadImage(uploadImageRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("get/transaction")
    public ResponseEntity<?> getTransactionByCustomerIdAndTransactionId(@Valid @RequestParam Long customerId, @Valid @RequestParam Long transactionId){
        return null;
    }

    @GetMapping("get/transactions/all/{customer_id}")
    public ResponseEntity<?> getAllTransactionsByCustomerId(@Valid @PathVariable Long customer_id){
        return null;
    }
    @DeleteMapping("delete/transaction")
    public ResponseEntity<?> deleteTransactionByCustomerIdAndTransactionId(@Valid @RequestParam Long customerId, @Valid @RequestParam Long transactionId){
        return null;
    }
    @DeleteMapping("delete/transaction/all/{customer_id}")
    public ResponseEntity<?> deleteAllTransactionsByCustomerId(@Valid @PathVariable Long customer_id){
        return null;
    }
}

