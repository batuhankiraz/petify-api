package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.events.UserRegisterMailEvent;
import com.estu.petify.petifycore.exceptions.PetifySendMailException;
import com.estu.petify.petifycore.service.PetifyMailService;
import com.estu.petify.petifyfacades.mail.content.builder.MailContentBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("petifyMailService")
@RequiredArgsConstructor
@Slf4j
public class DefaultPetifyMailService implements PetifyMailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    @Override
    public void sendUserRegisterMail(final UserRegisterMailEvent userRegisterMailEvent) {
        final MimeMessagePreparator messagePreparator = mimeMessage -> {

            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@petify.com");
            messageHelper.setTo(userRegisterMailEvent.getUser());
            messageHelper.setSubject(userRegisterMailEvent.getSubject());
            messageHelper.setText(mailContentBuilder.buildUserRegisterMail(userRegisterMailEvent.getMessage(), userRegisterMailEvent.getUser()));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("INFO: Mail has been successfully send to " + userRegisterMailEvent.getUser());
        } catch (final MailException e) {
            throw new PetifySendMailException("Couldn't send" +
                    " Account Verification Mail to " + userRegisterMailEvent.getUser());
        }
    }


}
