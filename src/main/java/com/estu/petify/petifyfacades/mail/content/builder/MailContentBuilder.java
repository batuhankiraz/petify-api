package com.estu.petify.petifyfacades.mail.content.builder;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailContentBuilder {

    private static final String REGISTER_MAIL_TEMPLATE = "user-register-mail";

    private final TemplateEngine templateEngine;

    public String buildUserRegisterMail(final String message, final String username) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("message", message);
        return templateEngine.process(REGISTER_MAIL_TEMPLATE, context);
    }
}
