package org.mangorage.mail.api.message;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public interface IMessageResponseBuilder {

    IMessageResponseBuilder setDefaultHeader() throws MessagingException;

    IMessageResponseBuilder setReplyContent(String text) throws MessagingException;


    Message build();
    default void send() throws MessagingException {
        Transport.send(build());
    };
}
