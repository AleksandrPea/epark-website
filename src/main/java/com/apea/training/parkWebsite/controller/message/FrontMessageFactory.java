package com.apea.training.parkWebsite.controller.message;

import java.util.HashMap;
import java.util.Map;

public class FrontMessageFactory {
    private Map<String, FrontendMessage> messagesSuccess = new HashMap<>();
    private Map<String, FrontendMessage> messagesInfo = new HashMap<>();
    private Map<String, FrontendMessage> messagesWarning = new HashMap<>();
    private Map<String, FrontendMessage> messagesError = new HashMap<>();

    private static FrontMessageFactory instance = new FrontMessageFactory();

    private FrontMessageFactory() {}

    public static FrontMessageFactory getInstance() {
        return instance;
    }

    public FrontendMessage getSuccess(String messageKey) {
        return getMessageFromCache(messagesSuccess, FrontendMessage.MessageType.SUCCESS, messageKey);
    }

    public FrontendMessage getInfo(String messageKey) {
        return getMessageFromCache(messagesInfo, FrontendMessage.MessageType.INFO, messageKey);
    }

    public FrontendMessage getWarning(String messageKey) {
        return getMessageFromCache(messagesWarning, FrontendMessage.MessageType.WARNING, messageKey);
    }

    public FrontendMessage getError(String messageKey) {
        return getMessageFromCache(messagesError, FrontendMessage.MessageType.ERROR, messageKey);
    }

    private FrontendMessage getMessageFromCache(Map<String, FrontendMessage> cache,
                                                FrontendMessage.MessageType messageType, String messageKey) {
        if (!cache.containsKey(messageKey)) {
            cache.put(messageKey, new FrontendMessage(messageKey, messageType));
        }

        return cache.get(messageKey);
    }
}
