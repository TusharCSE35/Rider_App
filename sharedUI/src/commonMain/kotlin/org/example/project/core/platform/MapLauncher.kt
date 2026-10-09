package org.example.project.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalUriHandler
import org.example.project.components.LocalToast

@Composable
fun rememberMapLauncher(): (Double, Double) -> Unit {
    val uriHandler = LocalUriHandler.current
    val toast = LocalToast.current
    return remember(uriHandler, toast) {
        { lat: Double, lng: Double ->
            if (lat == 0.0 && lng == 0.0) {
                toast.error("Location not available")
            } else {
                try {
                    uriHandler.openUri("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng&travelmode=two-wheeler")
                } catch (e: Exception) {
                    toast.error("Unable to open maps")
                }
            }
        }
    }
}