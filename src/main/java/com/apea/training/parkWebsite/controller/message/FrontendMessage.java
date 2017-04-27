package com.apea.training.parkWebsite.controller.message;

public class FrontendMessage {
    private String messageKey;
    private MessageType type;

    enum MessageType {
        SUCCESS,
        INFO,
        WARNING,
        ERROR
    }

    public FrontendMessage(String messageKey, MessageType type) {
        this.messageKey = messageKey;
        this.type = type;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public MessageType getType() {
        return type;
    }
}
