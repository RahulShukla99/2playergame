package com.messaging.thread;

import com.messaging.dto.Message;
import com.messaging.dto.impl.BasicPlayer;
import com.messaging.dto.impl.MessageDTO;
import com.messaging.exception.ExceptionHandler;
import com.messaging.service.impl.PlayerService;
import com.messaging.util.ConfigReader;
import com.messaging.util.Logger;

import static com.messaging.util.Constants.*;

/**
 * Main class to start separate-thread messaging.
 * 
 * @author Rahul Shukla
 */
public class SeparateThreadMain {
    public static void main(String[] args) {
        Logger.debug("PROCESS BUILDER FOR SEPARATE PID :: ", " START ");

        PlayerService playerService = new PlayerService();

        BasicPlayer basicPlayerOne = new BasicPlayer(PLAYER_ONE, playerService);
        BasicPlayer basicPlayerTwo = new BasicPlayer(PLAYER_TWO, playerService);

        basicPlayerOne.setOtherPlayer(basicPlayerTwo);
        basicPlayerTwo.setOtherPlayer(basicPlayerOne);

        Thread threadOne = new Thread(basicPlayerOne, PLAYER_ONE_THREAD);
        Thread threadTwo = new Thread(basicPlayerTwo, PLAYER_TWO_THREAD);

        threadOne.start();
        threadTwo.start();

        Message messageOne = new MessageDTO(PLAYER_ONE, PLAYER_TWO, START, ONE);
        basicPlayerOne.receiveMessage(messageOne);

        try{
            Thread.sleep(ConfigReader.getIntProperty(THREAD_SLEEP_MILLISECONDS, 3000));
        } catch (Exception ex) {
            ExceptionHandler.handle("GRACEFUL EXIT - EXCEPTION IN SINGLE THREAD :: ", ex);
        }

        basicPlayerOne.stop();
        basicPlayerTwo.stop();

        threadOne.interrupt();
        threadTwo.interrupt();

        Logger.debug("PROCESS BUILDER FOR SEPARATE PID :: ", " EXIT ");
    }
}
