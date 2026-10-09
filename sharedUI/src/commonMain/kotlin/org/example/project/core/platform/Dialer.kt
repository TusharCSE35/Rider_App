package org.example.project.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalUriHandler
import org.example.project.components.LocalToast

@Composable
fun rememberDialer(): (String) -> Unit {
    val uriHandler = LocalUriHandler.current
    val toast = LocalToast.current
    return remember(uriHandler, toast) {
        { phone: String ->
            try {
                uriHandler.openUri("tel:$phone")
            } catch (e: Exception) {
                toast.error("Unable to open dialer")
            }
        }
    }
}