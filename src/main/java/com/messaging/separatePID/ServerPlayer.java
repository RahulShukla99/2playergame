package com.messaging.separatePID;

import com.messaging.exception.ExceptionHandler;
import com.messaging.util.ConfigReader;
import com.messaging.util.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.messaging.util.Constants.SERVER_PORT;

/**
 * Represents Player 2 running in a separate PID as a TCP server
 * and contains the server logic to open ServerSocket
 *
 * @author Rahul Shukla
 */
public class ServerPlayer {
    public static void main(String[] args) {
        Logger.info("SERVER MAIN THREAD :: ", " START ");

        try (ServerSocket serverSocket = new ServerSocket(ConfigReader.getIntProperty(SERVER_PORT, 8080));
             Socket clientSocket = serverSocket.accept();
             BufferedReader bufferedReaderIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter printWriterOut = new PrintWriter(clientSocket.getOutputStream(), true)) {
             Logger.info("CLIENT CONNECTED ON PORT :: ", String.valueOf(clientSocket));

            String inputLine;
            while ((inputLine = bufferedReaderIn.readLine()) != null) {
                Logger.info("SERVER RECEIVED MESSAGE :: ", inputLine);

                String message = "REPLY - " + inputLine;
                printWriterOut.println(message);
                Logger.info("SERVER SENT MESSAGE :: ",  message);
            }

        } catch (Exception exception) {
            ExceptionHandler.handle("EXCEPTION IN MAIN THREAD - SERVER :: " , exception);
        }
        Logger.info("SERVER DISCONNECTED :: ", "EXIT");
    }
}
