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
    @NotNull(message = "user id cannot be null")
    private Long customerId;

    @NotNull(message = "field profile image cannot be null")
    private MultipartFile profileImage;
}
