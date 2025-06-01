package service;

import com.messaging.dto.Message;
import com.messaging.dto.Player;
import com.messaging.dto.impl.MessageDTO;
import com.messaging.service.impl.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Suite to validate player service for different scenarios.
 *
 * Author: Rahul Shukla
 */
public class PlayerServiceTest {
    private StringBuilder messageCollector;
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        messageCollector = new StringBuilder();
        Player mockPlayer = new Player() {
            @Override
            public void sendMessage(Message message) {
                if (message != null) {
                    messageCollector.append(message.getContent()).append("\n");
                } else {
                    messageCollector.append("NULL_MESSAGE_RECEIVED\n");
                }
            }
            @Override
            public String getPlayerId() {
                return "Player_Test";
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

        String[] lines = messageCollector.toString().split("\\n");
        assertEquals(10, lines.length);
        assertTrue(lines[0].startsWith("Ping"));
    }

    @Test
    void testEmptyMessageContent() {
        Message message = new MessageDTO("Sender", "TestPlayer", "", 1);
        playerService.handleMessage(message);
        assertEquals("\n", messageCollector.toString());
    }

    @Test
    void testLargeMessageContent() {
        String largeContent = "X".repeat(10_000); // 10 KB
        Message message = new MessageDTO("Sender", "TestPlayer", largeContent, 1);
        playerService.handleMessage(message);

        String result = messageCollector.toString().trim();
        assertEquals(10_000, result.length());
        assertTrue(result.startsWith("X") && result.endsWith("X"));
    }

    @Test
    void testNullMessageHandling() {
        playerService.handleMessage(null);
        String[] logs = messageCollector.toString().split("\\n");
        assertEquals(1, logs.length);
        assertEquals("NULL_MESSAGE_RECEIVED", logs[0]);
    }

    @Test
    void testUnicodeMessageHandling() {
        String unicodeMessage = "你好 👋🏼 — Привет — مرحبا";
        Message message = new MessageDTO("Sender", "TestPlayer", unicodeMessage, 42);
        playerService.handleMessage(message);

        String output = messageCollector.toString().trim();
        assertEquals(unicodeMessage, output);
    }

}
