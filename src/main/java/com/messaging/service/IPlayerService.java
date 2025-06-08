package com.messaging.service;

import com.messaging.dto.Message;
import com.messaging.dto.Player;

/**
 * Service interface for player operations.
 * 
 * @author Rahul Shukla
 */
public interface IPlayerService {
    void handleMessage(Message message);
    void setPlayers(Player initiator, Player responder);
    void initiateMessageExchange();
}
