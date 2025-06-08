package com.messaging.dto.impl;

import com.messaging.dto.Message;

import java.time.Instant;
import java.util.UUID;

/**
 * Encapsulates message metadata and body and includes
 * the default public getters
 * 
 * @author Rahul Shukla
 */
public class MessageDTO implements Message {
    private final String senderId;
    private final String receiverId;
    private final String content;
    private final int messageCounter;
    private final Instant timestamp;
    private final String messageId;

    public MessageDTO(String senderId, String receiverId, String content, int messageCounter) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.messageCounter = messageCounter;
        this.timestamp = Instant.now();
        this.messageId = UUID.randomUUID().toString();
    }

    @Override
    public String getSenderId() {
        return senderId;
    }

    @Override
    public String getReceiverId() {
        return receiverId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getMessageCounter() {
        return messageCounter;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return "MessageDTO{ " +
                " senderId= " + senderId +
                " receiverId= " + receiverId +
                " content= " + content +
                " timestamp= " + timestamp +
                " messageCounter= " + messageCounter +
                " messageId= " + messageId + " }";
    }
}
