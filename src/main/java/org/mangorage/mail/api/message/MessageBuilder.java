package org.mangorage.mail.api.message;


import org.mangorage.general.Utils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Builder for {@link javax.mail.internet.MimeMessage}
 */
public abstract class MessageBuilder {
    public static IMessageBuilder sendNewMessage(Session session) {
        return new DefaultMessageBuilder(new MimeMessage(session));
    }

    public static IMessageResponseBuilder reply(Message message, Session session) {
        return new DefaultMessageResponseBuilder(message, new MimeMessage(session));
    }

    private record DefaultMessageBuilder(MimeMessage message) implements IMessageBuilder {
        @Override
        public IMessageBuilder setSentFrom(String sentFrom, String username) throws MessagingException, UnsupportedEncodingException {
            message.setFrom(new InternetAddress(sentFrom, username));
            return this;
        }

        @Override
        public IMessageBuilder setDefaultHeader() throws MessagingException {
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            return this;
        }

        @Override
        public IMessageBuilder addHeader(String key, String value) throws MessagingException {
            message.addHeader(key, value);
            return this;
        }

        @Override
        public IMessageBuilder setSubject(String subject) throws MessagingException {
            message.setSubject(subject);
            return this;
        }

        @Override
        public IMessageBuilder setText(String text) throws MessagingException {
            message.setText(text);
            return this;
        }

        @Override
        public IMessageBuilder setRecipients(Message.RecipientType recipientType, List<String> recipients) throws MessagingException {
            message.setRecipients(recipientType, Utils.getAddresses(recipients));
            return this;
        }

        @Override
        public IMessageBuilder setReplyTo(List<String> replyTo) throws MessagingException {
            message.setReplyTo(Utils.getAddresses(replyTo));
            return this;
        }


        @Override
        public Message build() {
            return message;
        }
    }

    private record DefaultMessageResponseBuilder(Message original, MimeMessage message) implements IMessageResponseBuilder {

        @Override
        public IMessageResponseBuilder setDefaultHeader() throws MessagingException {

            Address[] fromAddresses = original.getReplyTo();
            if (fromAddresses == null || fromAddresses.length == 0) {
                fromAddresses = original.getFrom();
            }
            message.setFrom(fromAddresses[0]); // Set From to original sender's address
            message.setRecipient(Message.RecipientType.TO, original.getFrom()[0]); // Set To to original sender's address
            message.setSubject("Re: " + original.getSubject()); // Add "Re:" to original subject

            // Set the In-Reply-To header
            String messageId = original.getHeader("Message-ID")[0];
            message.setHeader("In-Reply-To", messageId);
            return this;
        }

        @Override
        public IMessageResponseBuilder setReplyContent(String text) throws MessagingException {
            message.setText(text);
            return this;
        }

        @Override
        public Message build() {
            return message;
        }
    }
}
