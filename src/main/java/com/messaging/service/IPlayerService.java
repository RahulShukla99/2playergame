package com.messaging.service;

import com.messaging.dto.Message;
import com.messaging.dto.Player;

/**
 * The interface defines a contract for player entities that are capable for sending
 * and receiving message objects. Implementation of the interface represents players
 * communicating within the same PID.
 */

/**
 * Service interface for player operations.
 * 
 * @author AutoDoc
 */
public interface IPlayerService {
    void handleMessage(Message message);
    void setPlayers(Player initiator, Player responder);
    void initiateMessageExchange();
}
