package org.mangorage.mail.api.message;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IMessageBuilder {


    IMessageBuilder setSentFrom(String sentFrom, String username) throws MessagingException, UnsupportedEncodingException;
    IMessageBuilder setDefaultHeader() throws MessagingException;
    IMessageBuilder setSubject(String subject) throws MessagingException;
    IMessageBuilder setText(String text) throws MessagingException;
    default IMessageBuilder setRecipient(Message.RecipientType recipientType, String recipient) throws MessagingException {
        return setRecipients(recipientType, List.of(recipient));
    }
    IMessageBuilder setRecipients(Message.RecipientType recipientType, List<String> recipients) throws MessagingException;
    IMessageBuilder setReplyTo(List<String> replyTo) throws MessagingException;
    default IMessageBuilder setReplyTo(String replyTo) throws MessagingException {
        return setReplyTo(List.of(replyTo));
    }
    IMessageBuilder addHeader(String key, String value) throws MessagingException;


    Message build();

    default void send() throws MessagingException {
        Transport.send(build());
    }
}
