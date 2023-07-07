package com.bank.E_Bank_App.service.cloud;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile image);
}
