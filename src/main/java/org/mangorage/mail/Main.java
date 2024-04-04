package org.mangorage.mail;

import org.mangorage.mail.api.Email;
import org.mangorage.mail.api.EmailWithSession;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class Main {
    public final static boolean SEND = false;

    public static void main(String[] args) {
        if (args.length == 0) return;

        var info = Configs.infoBasicConfig.get();
        var defaultInfo = Configs.defaultInfoConfig.get();

        final String username = info.username(); //requires valid gmail id

        final Email email = Email.of(username, info.password());
        final EmailWithSession SMTPSession = email.createDefaultGmailSMTPSession();
        final EmailWithSession IMAPSSession = email.createDefaultGmailImapsSession();

        if (SEND) {
            try {
                SMTPSession.createEmail()
                        .setDefaultHeader()
                        .setReplyTo(defaultInfo.replyTo())
                        .setRecipients(Message.RecipientType.TO, defaultInfo.recipients())
                        .setSubject("Testing")
                        .setText("""
                                If you got this, that means the new API worked!
                                """)
                        .setSentFrom(defaultInfo.sentFrom(), defaultInfo.username())
                        .send();
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        IMAPSSession.retrieveMessagesAsync(m -> {
            System.out.println(m.getSubject());
            SMTPSession.createEmailResponse(m)
                    .setDefaultHeader()
                    .setReplyContent("""
                        Hello there! I will respond to you as soon as possible!
                        
                        
                        Thanks!
                     
                        
                        This is an automated response.
                    """)
                    .send();
        }, 30, TimeUnit.SECONDS);
        System.out.println("TEST!");
    }
}
