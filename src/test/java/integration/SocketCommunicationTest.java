package integration;

import com.messaging.separatePID.ClientPlayer;
import com.messaging.separatePID.ServerPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SocketCommunicationTest {

    @Test
    void testClientServerCommunication() {
        Thread serverThread = new Thread(() -> ServerPlayer.main(null));

        Thread clientThread = new Thread(() -> {
            try{
                Thread.sleep(1000); // WAIT FOR THE SERVER TO BE READY
                ClientPlayer.main(null);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        assertDoesNotThrow(()-> {
            serverThread.start();
            clientThread.start();

            serverThread.join(6000);
            clientThread.join(6000);
        });
    }
}
