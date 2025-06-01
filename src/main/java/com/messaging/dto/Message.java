package com.messaging.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * Represents the message data exchanged between players.
 * 
 * @author Rahul Shukla
 */
public interface Message extends Serializable {
    String getSenderId();
    String getReceiverId();
    String getContent();
    int getMessageCounter();
    Instant getTimestamp();
    String getMessageId();
}
