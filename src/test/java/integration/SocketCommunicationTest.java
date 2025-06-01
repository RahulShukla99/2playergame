package integration;

import com.messaging.separatePID.ClientPlayer;
import com.messaging.separatePID.ServerPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Test Suite to validate client server socket communication for different scenarios.
 *
 * Author: Rahul Shukla
 */
public class SocketCommunicationTest {

    @Test
    void testClientServerCommunication() {
        Thread serverThread = new Thread(() -> {
            try {
                ServerPlayer.main(null);
            } catch (Exception e) {
                System.err.println("ServerPlayer encountered an error: " + e.getMessage());
                e.printStackTrace();
            }
        }, "ServerThread");

        Thread clientThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // WAIT FOR THE SERVER TO BE READY
                ClientPlayer.main(null);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("ClientPlayer thread was interrupted.");
            } catch (Exception e) {
                System.err.println("ClientPlayer encountered an error: " + e.getMessage());
                e.printStackTrace();
            }
        }, "ClientThread");

        assertDoesNotThrow(() -> {
            serverThread.start();
            clientThread.start();

            serverThread.join(6000);
            clientThread.join(6000);
        });

        assertFalse(serverThread.isAlive(), "Server thread should have finished");
        assertFalse(clientThread.isAlive(), "Client thread should have finished");
    }
}
