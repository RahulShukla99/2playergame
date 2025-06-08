package integration;

import com.messaging.dto.Message;
import com.messaging.dto.impl.BasicPlayer;
import com.messaging.dto.impl.MessageDTO;
import com.messaging.service.impl.PlayerService;
import com.messaging.util.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test Suite to validate messaging across players for different scenarios.
 *
 * Author: Rahul Shukla
 */
public class PlayerIntegrationTest {

    @DisplayName(" Should exchange messages between two players using threads")
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

    @DisplayName(" Should exchange messages between two players using threads")
    @Test
    void testTenMessageExchangeBetweenPlayers() throws InterruptedException {
        PlayerService playerService = new PlayerService();
        BasicPlayer playerOne = new BasicPlayer("Player_1", playerService);
        BasicPlayer playerTwo = new BasicPlayer("Player_2", playerService);

        playerOne.setOtherPlayer(playerTwo);
        playerTwo.setOtherPlayer(playerOne);

        Thread t1 = new Thread(playerOne);
        Thread t2 = new Thread(playerTwo);

        t1.start();
        t2.start();

        playerOne.receiveMessage(new MessageDTO("Player_1", "Player_2", "Hello", 1));

        Thread.sleep(3000); // Allow time for 10 exchanges

        playerOne.stop();
        playerTwo.stop();

        t1.join(1000);
        t2.join(1000);
    }

    @DisplayName(" Should exchange messages between two players using threads")
    @Test
    void testNullMessageHandlingBetweenPlayers() throws InterruptedException {
        PlayerService playerService = new PlayerService();
        BasicPlayer playerOne = new BasicPlayer("Player_1", playerService);
        BasicPlayer playerTwo = new BasicPlayer("Player_2", playerService);

        playerOne.setOtherPlayer(playerTwo);
        playerTwo.setOtherPlayer(playerOne);

        Thread t1 = new Thread(playerOne);
        Thread t2 = new Thread(playerTwo);

        t1.start();
        t2.start();

        // Intentionally send null message
        playerOne.receiveMessage(null);

        Thread.sleep(1000);

        playerOne.stop();
        playerTwo.stop();

        t1.join(1000);
        t2.join(1000);
    }

    @DisplayName(" Should exchange messages between two players using threads")
    @Test
    void testOneWayMessageOnly() throws InterruptedException {
        PlayerService playerService = new PlayerService() {
            @Override
            public void handleMessage(Message message) {
                // One-way: don't send a response
                Logger.info("Test", "Handled message: " + message.getContent());
            }
        };

        BasicPlayer playerOne = new BasicPlayer("Player_1", playerService);
        BasicPlayer playerTwo = new BasicPlayer("Player_2", playerService);

        playerOne.setOtherPlayer(playerTwo);
        playerTwo.setOtherPlayer(playerOne);

        Thread t1 = new Thread(playerOne);
        Thread t2 = new Thread(playerTwo);

        t1.start();
        t2.start();

        playerOne.receiveMessage(new MessageDTO("Player_1", "Player_2", "Hello without reply", 1));

        Thread.sleep(1000);

        playerOne.stop();
        playerTwo.stop();

        t1.join(1000);
        t2.join(1000);
    }

    @DisplayName(" Should exchange messages between two players using threads")
    @Test
    void testPlayerStopsMidExchange() throws InterruptedException {
        PlayerService playerService = new PlayerService();
        BasicPlayer playerOne = new BasicPlayer("Player_1", playerService);
        BasicPlayer playerTwo = new BasicPlayer("Player_2", playerService);

        playerOne.setOtherPlayer(playerTwo);
        playerTwo.setOtherPlayer(playerOne);

        Thread t1 = new Thread(playerOne);
        Thread t2 = new Thread(playerTwo);

        t1.start();
        t2.start();

        playerOne.receiveMessage(new MessageDTO("Player_1", "Player_2", "Start and stop", 1));

        Thread.sleep(1000);

        playerOne.stop(); // Only stop one player mid-exchange

        Thread.sleep(1000);

        playerTwo.stop();

        t1.join(1000);
        t2.join(1000);
    }
}
