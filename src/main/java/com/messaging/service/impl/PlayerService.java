package com.messaging.service.impl;

import com.messaging.dto.Message;
import com.messaging.dto.Player;
import com.messaging.dto.impl.MessageDTO;
import com.messaging.exception.ExceptionHandler;
import com.messaging.service.IPlayerService;
import com.messaging.util.ConfigReader;
import com.messaging.util.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static com.messaging.util.Constants.*;

/**
 * Service implementation for player logic.
 *
 * Author: Rahul Shukla
 */
public class PlayerService implements IPlayerService {
    private Player initiatingPlayer;
    private Player respondingPlayer;
    private int messageLimit;

    private final AtomicInteger totalExchanges = new AtomicInteger(ZERO);

    @Override
    public void setPlayers(Player initiator, Player responder) {
        this.initiatingPlayer = initiator;
        this.respondingPlayer = responder;
        this.messageLimit = ConfigReader.getIntProperty(PLAYER_MSG_LIMIT, TEN);
        this.initiatingPlayer.setOtherPlayer(this.respondingPlayer);
        this.respondingPlayer.setOtherPlayer(this.initiatingPlayer);
    }

    @Override
    public void initiateMessageExchange() {
//        Logger.log(" ENTER INITIATE MESSAGE EXCHANGE :: ");
        for (int i = 1; i <= messageLimit; i++) {
            Message message = new MessageDTO("PLAYER_ONE", "PLAYER_TWO", "msg-" + i, i);
            initiatingPlayer.sendMessage(message);
        }
//        Logger.log(" EXIT INITIATE MESSAGE EXCHANGE");
    }

    @Override
    public void handleMessage(Message message) {
//        Logger.log("ENTER - MESSAGE HANDLER :: ");
        try {
            Logger.log(" RECEIVED MESSAGE :: " + message.getContent());

            // Only respond if the message came from the initiator
            if (!message.getSenderId().equals(respondingPlayer.getPlayerId())) {
                if (message.getMessageCounter() >= messageLimit) {
                    Logger.log(" REQUIRED MESSAGES EXCHANGED - EXITING :: ");
                    return;
                }

                Message response = new MessageDTO(
                        respondingPlayer.getPlayerId(),
                        message.getSenderId(),
                        message.getContent() + " - REPLY " + message.getMessageCounter(),
                        message.getMessageCounter() + ONE
                );

                respondingPlayer.sendMessage(response);
            }

        } catch (Exception exception) {
            ExceptionHandler.handle("EXCEPTION IN MESSAGE HANDLER, PLAYER_ID - " + respondingPlayer.getPlayerId(), exception);
        }
//        Logger.log("EXIT - MESSAGE HANDLER :: ");
    }
}
