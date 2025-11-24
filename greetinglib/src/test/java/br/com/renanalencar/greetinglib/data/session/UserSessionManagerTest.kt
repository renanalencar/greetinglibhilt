package br.com.renanalencar.greetinglib.data.session

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for UserSessionManager.
 * Tests session management functionality.
 */
class UserSessionManagerTest {

    private val sessionManager = UserSessionManager()

    @Test
    fun startSession_should_create_new_session() {
        // Given
        val userId = "user123"
        val userName = "Alice"
        val isFormal = true

        // When
        sessionManager.startSession(userId, userName, isFormal)
        val session = sessionManager.getCurrentSession()

        // Then
        assertNotNull(session)
        assertEquals(userId, session?.userId)
        assertEquals(userName, session?.userName)
        assertEquals(isFormal, session?.isFormalPreference)
        assertTrue(sessionManager.isSessionActive())
    }

    @Test
    fun startSession_should_replace_existing_session() {
        // Given
        sessionManager.startSession("user1", "Alice", false)
        val firstSession = sessionManager.getCurrentSession()

        // When
        sessionManager.startSession("user2", "Bob", true)
        val secondSession = sessionManager.getCurrentSession()

        // Then
        assertNotNull(firstSession)
        assertNotNull(secondSession)
        assertNotEquals(firstSession, secondSession)
        assertEquals("user2", secondSession?.userId)
        assertEquals("Bob", secondSession?.userName)
        assertTrue(secondSession?.isFormalPreference == true)
    }

    @Test
    fun endSession_should_clear_current_session() {
        // Given
        sessionManager.startSession("user123", "Charlie", false)
        assertTrue(sessionManager.isSessionActive())

        // When
        sessionManager.endSession()

        // Then
        assertNull(sessionManager.getCurrentSession())
        assertFalse(sessionManager.isSessionActive())
    }

    @Test
    fun isSessionActive_should_return_false_when_no_session() {
        // Given - no session started

        // When
        val isActive = sessionManager.isSessionActive()

        // Then
        assertFalse(isActive)
        assertNull(sessionManager.getCurrentSession())
    }

    @Test
    fun userSession_should_store_session_start_time() {
        // Given
        val beforeTime = System.currentTimeMillis()
        sessionManager.startSession("user123", "Diana", true)
        val afterTime = System.currentTimeMillis()
        
        // When
        val session = sessionManager.getCurrentSession()

        // Then
        assertNotNull(session)
        assertTrue(session!!.sessionStartTime >= beforeTime)
        assertTrue(session.sessionStartTime <= afterTime)
    }

    @Test
    fun userSession_defaults_should_work_correctly() {
        // Given & When
        sessionManager.startSession("user123", "Eve") // Using default isFormalPreference
        val session = sessionManager.getCurrentSession()

        // Then
        assertNotNull(session)
        assertEquals("user123", session?.userId)
        assertEquals("Eve", session?.userName)
        assertEquals(false, session?.isFormalPreference) // Should default to false
    }
}