package com.example.kotlinfutures

import com.example.kotlinfutures.model.common.Encryption
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val pass = "kokime"
        val temp = Encryption.encrypt("kokime", "abra")
        assertEquals(Encryption.decrypt(temp, "abra"), pass)
    }
}
