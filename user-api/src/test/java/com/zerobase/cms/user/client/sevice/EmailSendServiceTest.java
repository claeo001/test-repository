package com.zerobase.cms.user.client.sevice;

import com.zerobase.cms.user.sevice.EmailSendService;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private EmailSendService emailSendService;

    @Test
    public void EmailTest(){
        String response = emailSendService.sendEmail();
        System.out.println(response);
    }

}