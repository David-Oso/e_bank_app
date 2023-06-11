//package com.bank.E_Bank_App.twilioSmsService;
//
//import com.bank.E_Bank_App.dto.request.twilio.SmsRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class SmsSenderImplTest {
//    @Autowired SmsSender smsSender;
//
//    @Test
//    void sendSms() {
//    SmsRequest smsRequest = new SmsRequest();
//    smsRequest.setRecipientPhoneNumber("+2349030400837");
//    smsRequest.setMessage(
//            String.format("Hey %s, this is a message from a springboot app testing twilio",
//                    smsRequest.getRecipientPhoneNumber()));
//    smsSender.sendSms(smsRequest);
//    }
//}