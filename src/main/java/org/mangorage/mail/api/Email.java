package org.mangorage.mail.api;

import org.mangorage.mail.api.property.EmailProperties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public final class Email {
    public static Email of(String username, String password) {
        return new Email(username, password);
    }

    private final Authenticator authentication;

    private Email(String username, String password) {
        this.authentication = new Authenticator() {
            private final PasswordAuthentication passwordAuthentication = new PasswordAuthentication(username, password);

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return passwordAuthentication;
            }
        };
    }

    public EmailWithSession createEmailSession(Properties properties) {
        return new EmailWithSession(
                Session.getInstance(properties, authentication)
        );
    }

    public EmailWithSession createDefaultGmailImapsSession() {
        return createEmailSession(EmailProperties.SMPTP_GMAIL_IMAPS_TLS);
    }

    public EmailWithSession createDefaultGmailSMTPSession() {
        return createEmailSession(EmailProperties.SMTP_GMAIL_SSL);
    }
}
