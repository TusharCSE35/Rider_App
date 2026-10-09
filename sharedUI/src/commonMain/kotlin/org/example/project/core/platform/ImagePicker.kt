package org.example.project.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

class ImagePickerLauncher(val launch: () -> Unit)

@Composable
expect fun rememberImagePicker(onPicked: (ImageBitmap?) -> Unit): ImagePickerLauncher