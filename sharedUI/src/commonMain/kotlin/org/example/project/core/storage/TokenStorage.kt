package org.example.project.core.storage

import com.russhwolf.settings.Settings

object TokenStorage {
    private val settings: Settings = Settings()

    private const val KEY_TOKEN = "auth_token"
    private const val KEY_PHONE = "auth_phone"

    val isLoggedIn: Boolean
        get() = !settings.getStringOrNull(KEY_TOKEN).isNullOrEmpty()

    fun getToken(): String? = settings.getStringOrNull(KEY_TOKEN)
    fun getPhone(): String? = settings.getStringOrNull(KEY_PHONE)

    fun saveSession(token: String, phone: String) {
        settings.putString(KEY_TOKEN, token)
        settings.putString(KEY_PHONE, phone)
    }

    fun clear() {
        settings.remove(KEY_TOKEN)
        settings.remove(KEY_PHONE)
    }
}