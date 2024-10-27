package com.huiun.util

import org.mindrot.jbcrypt.BCrypt

object PasswordUtil {
    fun hashPassword(password: String): String {
        // Generates a salt and hashes the password
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verifyPassword(password: String, hashed: String): Boolean {
        // Verifies the provided password against the hashed password
        return BCrypt.checkpw(password, hashed)
    }
}