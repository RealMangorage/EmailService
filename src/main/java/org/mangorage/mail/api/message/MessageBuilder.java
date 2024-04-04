package org.mangorage.mail.api.message;


import org.mangorage.general.Utils;

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
}
