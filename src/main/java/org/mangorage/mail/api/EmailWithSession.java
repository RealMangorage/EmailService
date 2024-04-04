package org.mangorage.mail.api;

import org.mangorage.mail.api.message.IMessageBuilder;
import org.mangorage.mail.api.message.MessageBuilder;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class EmailWithSession {
    private final Session session;

    protected EmailWithSession(Session session) {
        this.session = session;
    }

    public void retrieveMessagesAsync(IMessageReceiver receiver, long UpdateRate, TimeUnit timeUnit) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    // Connect to the email server
                    System.out.println("Attempting to get latest Mail...");
                    Store store = session.getStore("imaps");
                    store.connect();

                    // Open the inbox folder
                    Folder inbox = store.getFolder("INBOX");
                    inbox.open(Folder.READ_ONLY);

                    // Fetch messages
                    for (Message message : inbox.getMessages()) {
                        receiver.onReceive(message);
                    }
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, TimeUnit.MILLISECONDS.convert(UpdateRate, timeUnit));
    }

    public void replyToMessage(Message originalMessage, String replyContent) {
        try {
            MimeMessage replyMessage = new MimeMessage(session);

            // Set From, To, Subject headers
            Address[] fromAddresses = originalMessage.getReplyTo();
            if (fromAddresses == null || fromAddresses.length == 0) {
                fromAddresses = originalMessage.getFrom();
            }
            replyMessage.setFrom(fromAddresses[0]); // Set From to original sender's address
            replyMessage.setRecipient(Message.RecipientType.TO, originalMessage.getFrom()[0]); // Set To to original sender's address
            replyMessage.setSubject("Re: " + originalMessage.getSubject()); // Add "Re:" to original subject

            // Set the In-Reply-To header
            String messageId = originalMessage.getHeader("Message-ID")[0];
            replyMessage.setHeader("In-Reply-To", messageId);

            // Set the reply content
            replyMessage.setText(replyContent);

            // Send the reply
            Transport.send(replyMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IMessageBuilder createEmail() {
        return MessageBuilder.sendNewMessage(session);
    }
}
