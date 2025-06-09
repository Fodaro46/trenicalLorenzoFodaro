package com.trenical.client.session;

import com.trenical.client.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    @BeforeEach
    void clearSession() {
        SessionManager.getInstance().logout();
    }

    @Test
    void testLoginAndGetCurrentUser() {
        User user = new User("user123", "user@example.com");
        SessionManager.getInstance().login(user);
        assertEquals("user123", SessionManager.getInstance().getCurrentUser().getUserId());
    }

    @Test
    void testIsLoggedIn() {
        assertFalse(SessionManager.getInstance().isLoggedIn());
        SessionManager.getInstance().login(new User("test123", "test@example.com"));
        assertTrue(SessionManager.getInstance().isLoggedIn());
    }

    @Test
    void testLogout() {
        SessionManager.getInstance().login(new User("pippo42", "pippo@example.com"));
        SessionManager.getInstance().logout();
        assertFalse(SessionManager.getInstance().isLoggedIn());
        assertNull(SessionManager.getInstance().getCurrentUser());
    }
}

