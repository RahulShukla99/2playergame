package com.messaging.dto.impl;

import com.messaging.dto.Message;
import com.messaging.dto.Player;
import com.messaging.service.impl.PlayerService;
import com.messaging.exception.ExceptionHandler;
import com.messaging.util.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implements Player with basic communication logic.
 * 
 * @author Rahul Shukla
 */
public class BasicPlayer implements Player, Runnable {
    private final String playerId;
    private Player otherPlayer;
    private final PlayerService playerService;
    private final BlockingQueue<Message> messageInbox = new LinkedBlockingQueue<>();
    private volatile boolean isRunning = true;

    /**
     * Constructor to initialize the player and player service.
     */
    public BasicPlayer(String playerId, PlayerService playerService) {
        this.playerId = playerId;
        this.playerService = playerService;
    }

    /**
     * Sets the reference to the other player for communication.
     */
    @Override
    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Send message to other player
     */
    @Override
    public void sendMessage(Message message) {
        Logger.info(" PLAYER_ID - " + playerId, " - SENDING MESSAGE :: " + message.getContent());

        // Send message to the other player’s inbox
        if (otherPlayer != null) {
            otherPlayer.receiveMessage(message);
        }
    }

    /**
     * Adds the received message to a BlockingQueue
     */
    @Override
    // Handles incoming messages, responds if limit not reached.
    public void receiveMessage(Message message) {
        messageInbox.offer(message);
        handleNextMessage();
    }

    private void handleNextMessage() {
        Message message = messageInbox.poll();
        if (message == null)
            return;

        // Only handle if message is not from self
        if (!message.getSenderId().equals(this.playerId)) {
            playerService.handleMessage(message);
        }
    }

    public void stop(){
        isRunning = false;
    }

    /**
     * Continuously processes incoming messages from the player's inbox while the thread is active.
     *
     * This method is the main execution loop for the player when run in a same thread. It:
     * <ul>
     *   <li>Waits for new messages in the {@code messageInbox} using {@code take()} (blocking call).</li>
     *   <li>Delegates each message to the {@code playerService} for processing.</li>
     *   <li>Handles any runtime exceptions via the {@code ExceptionHandler}.</li>
     * </ul>
     *
     * The loop continues to run as long as {@code isRunning} remains {@code true}.
     * Once stopped, a final log entry is made to indicate thread shutdown.
     *
     * Note: In separate PID mode, communication is handled via sockets (TCP client/server),
     * and not by thread-based message polling loops. The run() method is irrelevant there,
     * because each player is run in a completely separate JVM process, and message handling
     * is done over network sockets (e.g., in ServerPlayer and ClientPlayer).
     */
    @Override
    public void run(){
        while(isRunning){
            Logger.debug(" RUNNING - PLAYER_ID :: ", playerId);
            try{
                Message message = messageInbox.take();
                playerService.handleMessage(message);
            } catch (Exception ex) {
                ExceptionHandler.handle(" EXCEPTION PROCESSING MESSAGE for PLAYER_ID - " + playerId, ex);
            }
        }
        Logger.info(" PLAYER_ID - " + playerId, " THREAD STOPPED");
    }

}
