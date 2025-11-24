package br.com.renanalencar.greetinglib

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Basic instrumented test for the greetinglib library.
 * Verifies that the library package is correctly configured.
 */
@RunWith(AndroidJUnit4::class)
class LibraryInstrumentedTest {
    
    @Test
    fun useAppContext() {
        // Context of the library under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("br.com.renanalencar.greetinglib.test", appContext.packageName)
    }
}