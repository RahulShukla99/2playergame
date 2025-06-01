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
            Logger.info("STARTING THE APPLICATION :: SEPARATE PID ", SEPARATE_PID_MODE);
            SeparateProcessRunner separateRunner = new SeparateProcessRunner();
            separateRunner.start();
        } else {
            Logger.info("STARTING THE APPLICATION :: SEPARATE_PID_MODE ", SEPARATE_PID_MODE);
            SameProcessRunner sameProcessRunner = new SameProcessRunner();
            sameProcessRunner.start();
        }
    }
}