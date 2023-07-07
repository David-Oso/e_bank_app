package com.bank.E_Bank_App.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UploadImageRequest {
    @NotNull(message = "User id cannot be null")
    private Long customerId;

    private MultipartFile profileImage;
}
