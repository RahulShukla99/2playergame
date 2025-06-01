package integration;

import com.messaging.dto.impl.BasicPlayer;
import com.messaging.dto.impl.MessageDTO;
import com.messaging.service.impl.PlayerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PlayerIntegrationTest {

    @Test
    void testPlayerInteractionWithThreads() throws InterruptedException {
        PlayerService playerService = new PlayerService();
        BasicPlayer playerOne = new BasicPlayer("Player_1", playerService);
        BasicPlayer playerTwo = new BasicPlayer("Player_2", playerService);

        playerOne.setOtherPlayer(playerTwo);
        playerTwo.setOtherPlayer(playerOne);

        Thread threadOne = new Thread(playerOne, "Player_1_Thread");
        Thread threadTwo = new Thread(playerTwo, "Player_2_Thread");

        assertDoesNotThrow(() -> {
            threadOne.start();
            threadTwo.start();

            playerOne.receiveMessage(new MessageDTO("Player_1", "Player_2", "Start", 1));

            Thread.sleep(3000); // WAIT FOR MESSAGE EXCHANGE

            playerOne.stop();
            playerTwo.stop();

            threadOne.interrupt();
            threadTwo.interrupt();
        });
    }
}
