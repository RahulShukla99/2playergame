package com.messaging;

import com.messaging.service.impl.SameProcessRunner;
import com.messaging.service.impl.SeparateProcessRunner;
import com.messaging.util.ConfigReader;
import com.messaging.util.Logger;

import static com.messaging.util.Constants.SEPARATE_PID_MODE;

/**
 * Main class to initialize 2 players in the same PID and start communication
 */

/**
 * Main entry point to run player communication system.
 * 
 * @author Rahul Shukla
 */
public class Main {
    public static void main(String[] args) {
        Logger.log("STARTING THE MESSAGING GAME :: ");

        if(ConfigReader.getBooleanProperty(SEPARATE_PID_MODE, false)){
            SeparateProcessRunner separateRunner = new SeparateProcessRunner();
            Logger.log("STARTING THE APPLICATION :: SEPARATE PID ");
            separateRunner.start();
        } else {
            Logger.log("STARTING THE APPLICATION :: SAME PID ");
            SameProcessRunner sameProcessRunner = new SameProcessRunner();
            sameProcessRunner.start();
        }
    }
}