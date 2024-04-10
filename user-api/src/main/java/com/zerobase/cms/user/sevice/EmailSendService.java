package com.zerobase.cms.user.sevice;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;


    public String sendEmail() {

        SendMailForm form = SendMailForm.builder()
                    .from("claeo001@daum.net")
                    .to("claeo001@naver.com")
                    .subject("Test email from zero base")
                    .text("my text")
                    .build();

       return mailgunClient.sendEmail(form).getBody();
    }
}
