package com.messaging.service.impl;

import com.messaging.exception.ExceptionHandler;
import com.messaging.service.IRunner;
import com.messaging.util.ConfigReader;
import com.messaging.util.Logger;

import static com.messaging.util.Constants.THREAD_SLEEP_MILLISECONDS;

/**
 *
 */
/**
 * Runner to start players in separate JVMs.
 * 
 * @author Rahul Shukla
 */
public class SeparateProcessRunner implements IRunner {
    @Override
    public void start() {
        try{
            ProcessBuilder processBuilderServer = new ProcessBuilder("java", "-cp","target/classes","com.messaging.separatePID.ServerPlayer");
            Logger.info("SERVER PLAYER STARTED ON PID :: ", String.valueOf(processBuilderServer.inheritIO().start().pid()));

            Thread.sleep(ConfigReader.getIntProperty(THREAD_SLEEP_MILLISECONDS,2000));

            ProcessBuilder processBuilderClient = new ProcessBuilder("java","-cp","target/classes","com.messaging.separatePID.ClientPlayer");
            Logger.info("CLIENT PLAYER STARTED ON PID :: ", String.valueOf(processBuilderClient.inheritIO().start().pid()));

        } catch (Exception exception) {
            ExceptionHandler.handle("EXCEPTION IN SEPARATE PROCESS BUILDER :: ", exception);
        }
    }
}
