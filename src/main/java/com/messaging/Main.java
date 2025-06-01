package com.messaging;

import com.messaging.service.impl.SameProcessRunner;
import com.messaging.service.impl.SeparateProcessRunner;
import com.messaging.util.ConfigReader;
import com.messaging.util.Logger;

import static com.messaging.util.Constants.SEPARATE_PID_MODE;

/**
 * Main entry point to run player communication system.
 * 
 * @author Rahul Shukla
 */
public class Main {
    public static void main(String[] args) {
        Logger.info("MAIN", "STARTING THE APPLICATION :: SAME PID");

        if(ConfigReader.getBooleanProperty(SEPARATE_PID_MODE, false)){
            Logger.log("STARTING THE APPLICATION :: SEPARATE PID ");
            SeparateProcessRunner separateRunner = new SeparateProcessRunner();
            separateRunner.start();
        } else {
            Logger.log("STARTING THE APPLICATION :: SAME PID ");
            SameProcessRunner sameProcessRunner = new SameProcessRunner();
            sameProcessRunner.start();
        }
    }
}