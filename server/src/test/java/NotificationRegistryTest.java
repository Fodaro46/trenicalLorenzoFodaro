
import com.trenical.grpc.Notifica;
import com.trenical.server.util.NotificationRegistry;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRegistryTest {

    private final String userId = "user-test";

    @BeforeEach
    void cleanBefore() {
        NotificationRegistry.markAllAsRead(userId);
    }

    @Test
    void testAddAndRetrieveNotifica() {
        Notifica n = Notifica.newBuilder()
                .setId("") // forzerà generazione UUID
                .setUserId(userId)
                .setMessaggio("Test notifica")
                .setTimestamp("2025-07-01T10:00")
                .build();

        NotificationRegistry.addNotification(userId, n);

        List<Notifica> unread = NotificationRegistry.getUnreadNotifications(userId);
        assertFalse(unread.isEmpty());
        assertTrue(unread.get(0).getMessaggio().contains("Test"));
    }

    @Test
    void testMarkAsRead() {
        Notifica n = Notifica.newBuilder()
                .setId("N-TEST")
                .setUserId(userId)
                .setMessaggio("Da leggere")
                .setTimestamp("2025-07-01T11:00")
                .build();

        NotificationRegistry.addNotification(userId, n);
        NotificationRegistry.markAsRead(userId, "N-TEST");

        List<Notifica> unread = NotificationRegistry.getUnreadNotifications(userId);
        assertTrue(unread.stream().noneMatch(x -> x.getId().equals("N-TEST")));
    }
}
