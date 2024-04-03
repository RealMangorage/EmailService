package org.mangorage.mail;

import org.mangorage.config.BasicConfig;
import org.mangorage.general.configs.EmailInfo;
import org.mangorage.mail.api.Email;
import org.mangorage.mail.api.EmailWithSession;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) return;

        var info = Configs.infoBasicConfig.get();

        final String username = info.username(); //requires valid gmail id

        final Email email = Email.of(username, info.password());
        final EmailWithSession SMTPSession = email.createDefaultGmailSMTPSession();
        final EmailWithSession IMAPSSession = email.createDefaultGmailImapsSession();

        IMAPSSession.retrieveMessagesAsync(m -> {

            if ((System.currentTimeMillis() - m.getSentDate().toInstant().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) <= 45_000) {
                System.out.println(m.getSubject());
                SMTPSession.replyToMessage(m, """
                        Hello there! I will respond to you as soon as possible!
                        
                        
                        Thanks!
                     
                        
                        This is an automated response.
                        """);
            }

        }, 30, TimeUnit.SECONDS);


        /**
        SMTPSession.sendEmail(
                username,
                "First Last",
                "F L",
                "Automated Email Sender",
                """
                        If you got this email, that means my automated email sending program worked!
                        
                        Thanks!
                        """
        );
         **/
    }
}
