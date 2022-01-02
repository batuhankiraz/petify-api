package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.events.UserRegisterMailEvent;
import com.estu.petify.petifycore.exceptions.PetifySendMailException;
import com.estu.petify.petifycore.service.PetifyMailService;
import com.estu.petify.petifyfacades.builder.MailContentBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("petifyMailService")
@AllArgsConstructor
@Slf4j
public class DefaultPetifyMailService implements PetifyMailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPetifyMailService.class);

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    @Override
    public void sendUserRegisterMail(UserRegisterMailEvent userRegisterMailEvent) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@petify.com");
            messageHelper.setTo(userRegisterMailEvent.getUser());
            messageHelper.setSubject(userRegisterMailEvent.getSubject());
            messageHelper.setText(mailContentBuilder.buildUserRegisterMail(userRegisterMailEvent.getMessage(), userRegisterMailEvent.getUser()));
        };
        try{
            mailSender.send(messagePreparator);
            LOGGER.info("Mail has been successfully send to " + userRegisterMailEvent.getUser());
        } catch (MailException e){
            throw new PetifySendMailException("Exception occurred when sending the mail to " + userRegisterMailEvent.getUser());
        }
    }


}
