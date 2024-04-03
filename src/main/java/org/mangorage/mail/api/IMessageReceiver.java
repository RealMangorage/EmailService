package org.mangorage.mail.api;

import javax.mail.Message;

public interface IMessageReceiver {
    void onReceive(Message message) throws Exception;
}
