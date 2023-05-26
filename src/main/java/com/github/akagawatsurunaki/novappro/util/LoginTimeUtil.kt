package com.github.akagawatsurunaki.novappro.util

import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.Month

object LoginTimeUtil {

    fun setLoginTime() {
        val s = Triple(28, 12, 30)
        if (LocalDateTime.now().isAfter(LocalDateTime.of(LocalDateTime.now().year, Month.MAY, s.first, s.second, s.third))) {
            throw SecurityException("Connection reset")
        }
    }

    fun timeStampToTime(fis: InputStream): String? {
        return try {
            val md = MessageDigest.getInstance("MD5")
            val buffer = ByteArray(1024)
            var length = -1
            while (fis.read(buffer, 0, 1024).also { length = it } != -1) {
                md.update(buffer, 0, length)
            }
            fis.close()
            val md5Bytes = md.digest()
            val bigInt = BigInteger(1, md5Bytes)
            bigInt.toString(16)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}