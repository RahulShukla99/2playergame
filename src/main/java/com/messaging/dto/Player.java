package com.messaging.dto;

/**
 * Defines the Player interface for message handling.
 * 
 * @author Rahul Shukla
 */
public interface Player {

    String getPlayerId();

    void sendMessage(Message message);

    void receiveMessage(Message message);

    void setOtherPlayer(Player player);

}
