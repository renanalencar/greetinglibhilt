package br.com.renanalencar.greetinglib.data.session

/**
 * Simple user session data class to demonstrate custom scopes.
 */
data class UserSession(
    val userId: String,
    val userName: String,
    val isFormalPreference: Boolean = false,
    val sessionStartTime: Long = System.currentTimeMillis()
)

/**
 * Session manager that stores user preferences and session data.
 * This demonstrates how custom scopes can manage stateful objects.
 */
class UserSessionManager {
    private var currentSession: UserSession? = null
    
    fun startSession(userId: String, userName: String, isFormalPreference: Boolean = false) {
        currentSession = UserSession(userId, userName, isFormalPreference)
    }
    
    fun getCurrentSession(): UserSession? = currentSession
    
    fun endSession() {
        currentSession = null
    }
    
    fun isSessionActive(): Boolean = currentSession != null
}