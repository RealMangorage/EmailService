package org.mangorage.mail.api;

import org.mangorage.mail.api.property.PropertyBuilder;

import java.util.Properties;

public final class EmailProperties {
    public static final Properties SMTP_GMAIL_SSL = PropertyBuilder.of()
            .put("mail.smtp.host", "smtp.gmail.com")
            .put("mail.smtp.socketFactory.port", "465")
            .put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            .put("mail.smtp.auth", "true")
            .put("mail.smtp.port", "465")
            .build();

    public static final Properties SMPTP_GMAIL_IMAPS_TLS = PropertyBuilder.of()
            .put("mail.store.protocol", "imaps")
            .put("mail.imaps.host", "imap.gmail.com")
            .put("mail.imaps.port", "993")
            .put("mail.imaps.starttls.enable", "true")
            .build();
}
