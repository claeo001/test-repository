package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.sevice.customer.SingUpCustomerService;
import com.zerobase.cms.user.sevice.seller.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SingUpCustomerService singUpCustomerService;
    private final SellerService sellerService;

    public void customerVerify(String email, String code){
        singUpCustomerService.verifyEmail(email,code);
    }

    public String customerSignUp(SignUpForm form){
        if (singUpCustomerService.isEmailExist(form.getEmail())){
            // exception
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = singUpCustomerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                        .from("tester@dannymytester.com")
                        .to(form.getEmail())
                        .subject("Verification Email")
                        .text(getVerificationEmailBody(c.getEmail(), c.getName(),"customer", code))
                            .build();
            log.info("send email result: " + mailgunClient.sendEmail(sendMailForm));
            return "회원 가입에 성공하셨습니다";
        }
    }
    public void sellerVerify(String email, String code){

        sellerService.verifyEmail(email,code);
    }

    public String sellerSignUp(SignUpForm form){
        if (singUpCustomerService.isEmailExist(form.getEmail())){
            // exception
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Seller s = sellerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("tester@dannymytester.com")
                    .to(form.getEmail())
                    .subject("Verification Email")
                    .text(getVerificationEmailBody(s.getEmail(), s.getName(),"seller", code))
                    .build();
            log.info("send email result:"+mailgunClient.sendEmail(sendMailForm));
            return "회원 가입에 성공하셨습니다";
        }
    }
    private String getRandomCode(){ return RandomStringUtils.random(10,true,true);
    }

    private String getVerificationEmailBody(String email, String name,String type, String code){
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello").append(name).append("! Please Click Link for verification.\n\n")
                .append("http://localhost:9090/signup/"+type+"verify/?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }

}
