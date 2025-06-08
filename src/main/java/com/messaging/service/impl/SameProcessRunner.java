package com.messaging.service.impl;

import com.messaging.dto.impl.BasicPlayer;
import com.messaging.service.IRunner;
import com.messaging.util.Logger;

import static com.messaging.util.Constants.PLAYER_ONE;
import static com.messaging.util.Constants.PLAYER_TWO;

/**
 * Runner to start players in same JVM.
 *
 * @author Rahul Shukla
 */
public class SameProcessRunner implements IRunner {

    @Override
    public void start() {
        Logger.debug("SAME PROCESS RUNNER :: ", " START ");

        PlayerService playerService = new PlayerService();

        BasicPlayer basicPlayerOne = new BasicPlayer(PLAYER_ONE, playerService);
        BasicPlayer basicPlayerTwo = new BasicPlayer(PLAYER_TWO, playerService);


        playerService.setPlayers(basicPlayerOne, basicPlayerTwo);
        playerService.initiateMessageExchange();

        Logger.debug("SAME PROCESS RUNNER :: ", " EXIT ");
    }
}
