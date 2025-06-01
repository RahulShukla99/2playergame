package com.messaging.separatePID;

import com.messaging.exception.ExceptionHandler;
import com.messaging.util.ConfigReader;
import com.messaging.util.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.messaging.util.Constants.*;

/**
 * Represents Player 1 running in a separate PID as a TCP client
 * and contains the client logic to connect to ServerSocket
 *
 * @author Rahul Shukla
 */
public class ClientPlayer {
    public static void main(String[] args) {
        Logger.log("ENTER - CLIENT MAIN THREAD :: ");

        Integer attempt, maxRetry;
        attempt = ZERO;
        maxRetry = ConfigReader.getIntProperty(MAX_RETRY, 5);
        Boolean isConnected = false;

        while (attempt < maxRetry && !isConnected) {
            try (Socket socket = new Socket(CLIENT_HOST, ConfigReader.getIntProperty(SERVER_PORT, 8080));
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriterOut = new PrintWriter(socket.getOutputStream(), true)) {

                for (int i = ONE; i <= ConfigReader.getIntProperty(PLAYER_MSG_LIMIT, 5); i++) {
                    String message = "Message - :: " + i;
                    Logger.info(" CLIENT SENT MESSAGE :: ", message);
                    printWriterOut.println(message);

                    String response = bufferedReader.readLine();
                    Logger.log(" CLIENT RECEIVED MESSAGE :: " + response);
                }
            } catch (Exception ex) {
                ExceptionHandler.handle("EXCEPTION IN CLIENT PLAYER :: ", ex);
                if (attempt < maxRetry) {
                    try {
                        Thread.sleep(ConfigReader.getIntProperty(RETRY_DELAY, 3));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        Logger.log("INTERRUPTED WHILE WAITING TO RETRY");
                        break;
                    }
                } else {
                    Logger.log("MAX RETRIES REACHED. UNABLE TO CONNECT.");
                }
            }
            Logger.log("EXIT - CLIENT DISCONNECTED :: ");
            attempt++;
        }
    }

}

