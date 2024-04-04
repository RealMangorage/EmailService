package org.mangorage.mail.api;

import org.mangorage.mail.api.message.IMessageBuilder;
import org.mangorage.mail.api.message.IMessageResponseBuilder;
import org.mangorage.mail.api.message.MessageBuilder;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.concurrent.TimeUnit;

public class EmailWithSession {
    private final Session session;

    protected EmailWithSession(Session session) {
        this.session = session;
    }

    public void retrieveMessagesAsync(IMessageReceiver receiver, long UpdateRate, TimeUnit timeUnit) {
        long time = TimeUnit.MILLISECONDS.convert(UpdateRate, timeUnit);
        new Thread((() -> {
            try {
                // Connect to the email server
                System.out.println("Attempting to get latest Mail...");
                Store store = session.getStore("imaps");
                store.connect();

                // Open the inbox folder
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);

                int amount = inbox.getMessageCount();

                while (true) {
                    var count = inbox.getMessageCount();
                    if (amount != count) {
                        for (Message message : inbox.getMessages(amount + 1, count)) {
                            receiver.onReceive(message);
                        }

                        amount = count;
                    }

                    Thread.sleep(time);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        })).start();
    }

    public IMessageBuilder createEmail() {
        return MessageBuilder.sendNewMessage(session);
    }

    public IMessageResponseBuilder createEmailResponse(Message message) {
        return MessageBuilder.reply(message, session);
    }
}
