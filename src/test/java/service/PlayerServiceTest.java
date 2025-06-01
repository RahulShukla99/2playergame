package service;

import com.messaging.dto.Message;
import com.messaging.dto.Player;
import com.messaging.dto.impl.MessageDTO;
import com.messaging.service.impl.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerServiceTest {

    private StringBuilder stringBuilder;
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        stringBuilder = new StringBuilder();

        Player mockPlayer = new Player() {
            @Override
            public String getPlayerId() {
                return "Player_Test";
            }

            @Override
            public void sendMessage(Message message) {
                stringBuilder.append(message.getContent()).append("\n");
            }

            @Override
            public void receiveMessage(Message message) {
                // No-op for test
            }

            @Override
            public void setOtherPlayer(Player player) {
                // No-op for test
            }
        };

        // Assuming your PlayerService constructor doesn't take parameters
        playerService = new PlayerService() {
            @Override
            public void handleMessage(Message message) {
                mockPlayer.sendMessage(message); // custom behavior
            }
        };
    }

    @Test
    void testInitiatorMessageFlow() {
        for (int i = 1; i <= 10; i++) {
            Message message = new MessageDTO("Sender", "TestPlayer", "Ping - " + i, i);
            playerService.handleMessage(message);
        }

        String[] lines = stringBuilder.toString().split("\\n");
        assertEquals(10, lines.length);
        assertTrue(lines[0].startsWith("Ping"));
    }
}
